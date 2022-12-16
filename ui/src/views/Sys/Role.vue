<template>
  <div class="page-container">
    <!--工具栏-->
    <div
      class="toolbar"
      style="float: left; padding-top: 10px; padding-left: 5px"
    >
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="角色名称"></el-input>
        </el-form-item>
        <el-form-item>
          <kt-button
            icon="fa fa-search"
            :label="$t('action.search')"
            perms="sys:role:view"
            type="primary"
            @click="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <kt-button
            icon="fa fa-plus"
            :label="$t('action.add')"
            perms="sys:user:add"
            type="primary"
            @click="handleAdd"
          />
        </el-form-item>
      </el-form>
    </div>

    <!--表格内容栏-->
    <kt-table
      permsEdit="sys:user:edit"
      permsDelete="sys:user:delete"
      permsThird="sys:user:delete"
      :showThird="true"
      thirdLabel="菜单"
      :width="280"
      :loading="loading"
      :data="pageResult"
      :columns="columns"
      @findPage="findPage"
      @handleEdit="handleEdit"
      @handleDelete="handleDelete"
      @handleThird="handleMenus"
      @handleCurrentChange="handleCurrentChange"
    >
    </kt-table>

    <!--角色的菜单授权-->
    <div class="menu-container" :v-if="true">
      <hr />
      <div class="menu-header">
        <span><B>角色所拥有的菜单授权</B></span>
      </div>
      <el-tree
        :data="menuData"
        size="mini"
        node-key="id"
        :props="defaultProps"
        style="width: 100%; pading-top: 20px; pading-left: 20px"
        ref="menuTree"
        v-loading="menuLoading"
        element-loading-text="拼命加载中"
      >
      </el-tree>
    </div>

    <!--新增编辑界面-->
    <el-dialog
      :title="operation ? '角色管理-新增' : '角色管理-修改'"
      width="30%"
      top="80px"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        label-width="80px"
        :rules="dataFormRules"
        ref="dataForm"
        :size="size"
        label-position="right"
      >
        <el-form-item label="角色编号" prop="code">
          <el-input v-model="dataForm.code" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="dataForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input v-model="dataForm.memo" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :size="size" @click.native="dialogVisible = false">{{
          $t("action.cancel")
        }}</el-button>
        <el-button
          :size="size"
          type="primary"
          @click.native="submitForm"
          :loading="editLoading"
          >{{ $t("action.submit") }}</el-button
        >
      </div>
    </el-dialog>

    <!-- 角色菜单设置界面 -->
    <el-dialog
      title="设置当前角色的菜单"
      width="45%"
      :visible.sync="menusVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        ref="menusForm"
        @keyup.enter.native="submitRoleMenus()"
        center
        :size="size"
        style="text-align: left"
      >
        <el-tree
          :data="sysMenuData"
          :props="sysMenuProps"
          show-checkbox
          default-expand-all
          node-key="id"
          ref="tree"
          highlight-current
          :accordion="true"
        >
        </el-tree>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="menusVisible = false">{{
          $t("action.cancel")
        }}</el-button>
        <el-button
          :size="size"
          type="primary"
          @click="submitRoleMenu()"
          v-loading.fullscreen.lock="editLoading"
          element-loading-text="数据保存中，请稍候……"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          >{{ $t("action.comfirm") }}</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import KtTable from "@/views/Core/KtTable";
import KtButton from "@/views/Core/KtButton";
export default {
  components: {
    KtTable,
    KtButton,
  },
  data() {
    return {
      loading: false,
      size: "small",
      filters: {
        name: "",
      },
      columns: [],
      pageRequest: {
        pageNum: 1,
        pageSize: 10,
        listCondition: [],
        listOrder: [],
      },
      pageResult: {},

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        code: [{ required: true, message: "请输入角色编号", trigger: "blur" }],
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        code: "",
        name: "",
        memo: "",
      },

      //主界面显示当前用户的相关菜单授权的数据
      menuLoading: false,
      menuData: [],
      defaultProps: {
        //映射关系
        label: "name",
        children: "children",
      },

      //以下是设置角色菜单的相关功能
      menusVisible: false,
      sysMenuData: [], //菜单结构树
      sysMenuProps: {
        //映射关系
        label: "name",
        children: "children",
      },
      roleId: 0,//当前选项的角色Id
      roleIdExisted: [], //给出当前菜单已经选择了的roleId
    };
  },
   watch: {
    roleIdExisted: function () {
       this.$nextTick(() => {
          this.$refs.tree.setCheckedKeys(this.roleIdExisted);
        });
    }, //选择的相关信息,用户能够方便的看出来
  },
  methods: {
    // 获取分页数据
    findPage: function (pageNum) {
      this.loading = true;
      if (pageNum != null) {
        this.pageRequest.pageNum = pageNum.pageNum;
      }
      this.pageRequest.listCondition = [];
      if (!(this.filters.name == "")) {
        this.pageRequest.listCondition = [
          {
            conditionLogic: "and",
            fieldOfJava: "name",
            fieldValue: this.filters.name,
            filedLogic: "lorm",
            newCondition: true,
          },
        ];
      }
      this.$api.role
        .findPage(this.pageRequest)
        .then((res) => {
          if (res.code == 200) {
            this.pageResult = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.pageResult = null;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.pageResult = null;
          this.loading = false;
        });
    },

    handleQuery: function () {
      this.findPage(1);
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true;
      this.operation = true;
      this.dataForm = {
        id: 0,
        code: "",
        name: "",
        memo: "",
      };
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true;
      this.operation = false;
      this.dataForm = Object.assign({}, params.row);
    },
    // 删除
    handleDelete(paramsIn) {
      let params = Object.assign({}, paramsIn.row);
      this.$confirm(
        "确认删除:角色名称为'" + params.name + "'的选中记录吗？",
        "提示",
        {
          type: "warning",
        }
      ).then(() => {
        this.$api.role
          .deleteById(params.id)
          .then((res) => {
            if (res.code == 200) {
              this.findPage(null);
              this.$message({ message: "删除成功", type: "success" });
            } else {
              this.$message({
                message: "操作失败, " + res.tip,
                type: "error",
              });
            }
          })
          .catch((err) => {
            this.$message({
              message: "操作失败, " + err.message,
              type: "error",
            });
          });
      });
    },

    handleCurrentChange(params){
      this.findRoleMenusTree(params.val.id);
    },

    // 编辑
    submitForm: function () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            let params = Object.assign({}, this.dataForm);
            this.$api.role.save(params).then((res) => {
              this.editLoading = false;
              if (res.code == 200) {
                this.$message({ message: "操作成功", type: "success" });
                this.dialogVisible = false;
                this.$refs["dataForm"].resetFields();
              } else {
                this.$message({
                  message: "操作失败, " + res.msg,
                  type: "error",
                });
              }
              this.findPage(null);
            });
          });
        }
      });
    },

    // 处理表格列过滤显示
    initColumns: function () {
      this.columns = [
        { prop: "code", label: "角色编码", minWidth: 50 },
        { prop: "name", label: "角色名称", minWidth: 50 },
        { prop: "memo", label: "备注", minWidth: 50 },
      ];
    },

    // 显示菜单设置界面
    handleMenus: function (paramsIn) {
      this.menusVisible = true;
      let params = Object.assign({}, paramsIn.row);
      this.roleId = params.id;
      this.findRoleMenusButton(this.roleId);
     
    },

    // 获取角色对应菜单的功能按钮选项（叶子节点，这样设置选择后，父节点及以上都半选择，符合程序要求）
    findRoleMenusButton: function (role_id) {
      this.$api.role
        .findRoleMenusButton(role_id)
        .then((res) => {
          if (res.code == 200) {
            this.roleIdExisted = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.roleIdExisted = [];
          }
        })
        .catch((err) => {
          this.$message({
            message: "操作失败1, " + err.message,
            type: "error",
          });
          this.roleIdExisted = [];
        });
    },

    // 获所有的菜单数据
    findRoleMenusTree: function (role_id) {
      this.menuLoading = true;
      this.$api.role
        .findRoleMenusTree(role_id)
        .then((res) => {
          if (res.code == 200) {
            this.menuData = res.data;
          } else {
            this.$message({ message: "操作失败2, " + res.tip, type: "error" });
            this.menuData = null;
          }

          this.menuLoading = false;
        })
        .catch((err) => {
          this.$message({
            message: "操作失败1, " + err.message,
            type: "error",
          });
          this.menuData = null;
          this.menuLoading = false;
        });
    },

    // 获所有的菜单数据
    findMenuTree: function () {
      this.$api.menu
        .findTree()
        .then((res) => {
          if (res.code == 200) {
            this.sysMenuData = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.sysMenuData = null;
          }

          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.sysMenuData = null;
          this.loading = false;
        });
    },

    //设置当前角色的菜单提交按钮的功能
    submitRoleMenu() {
      let menuId = this.$refs.tree
        .getCheckedKeys()
        .concat(this.$refs.tree.getHalfCheckedKeys());

      let params = {
        role_id: this.roleId,
        menu_ids: menuId,
      };
      this.$api.role
        .saveMenus(params)
        .then((res) => {
          if (res.code == 200) {
            this.$message({ message: "操作成功", type: "success" });
            this.menusVisible = false;
            this.$refs["menusForm"].resetFields();
          } else {
            this.$message({
              message: "操作失败, " + res.msg,
              type: "error",
            });
          }
          //  this.findTreeData();
          this.editLoading = false;
        })
        .catch((err) => {
          this.$message({
            message: "操作失败, " + err.message,
            type: "error",
          });
          this.editLoading = false;
        });
    },
  },
  mounted() {
    this.initColumns();
    this.findMenuTree();
  },
};
</script>

<style scoped>
.menu-container {
  margin-top: 30px;
}
.menu-header {
  padding-left: 8px;
  padding-bottom: 5px;
  text-align: left;
  font-size: 16px;
  color: rgb(20, 89, 121);
}
</style>