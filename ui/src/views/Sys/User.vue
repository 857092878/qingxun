<template>
  <div class="page-container">
    <el-container>
      <el-aside width="200px">
        <div class="aside-header">
          <span><B>部门信息列表</B></span>
        </div>
        <el-tree
          :data="sysDeptData"
          :props="sysDeptProps"
          @node-click="handleNodeClick"
        >
        </el-tree>
      </el-aside>
      <el-main>
        <!--工具栏-->
        <div
          class="toolbar"
          style="float: left; padding-top: 10px; padding-left: 5px"
        >
          <el-form :inline="true" :model="filters" :size="size">
            <el-form-item>
              <el-input
                v-model="filters.username"
                placeholder="用户名称"
              ></el-input>
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
          thirdLabel="角色"
          :width="280"
          :loading="loading"
          :data="pageResult"
          :columns="columns"
          @findPage="findPage"
          @handleEdit="handleEdit"
          @handleDelete="handleDelete"
          @handleThird="handleRoles"
        >
        </kt-table>
      </el-main>
    </el-container>

    <!--新增编辑界面-->
    <el-dialog
      :title="operation ? '用户管理-新增' : '用户管理-修改'"
      width="60%"
      top="40px"
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
        <el-form-item label="所属部门" prop="dept_id">
          <popup-tree-input
            :data="dialogDeptData"
            :props="dialogDeptProps"
            :prop="dataForm.dept_id_name"
            :nodeKey="'' + dataForm.deptId"
            :currentChangeHandle="deptTreeCurrentChangeHandle"
          >
          </popup-tree-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="dataForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="dataForm.nickname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="dataForm.password"
            type="password"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dataForm.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="dataForm.mobile" auto-complete="off"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="dataForm.status"
                placeholder="请选择"
                style="width: 100%"
              >
                <el-option
                  v-for="item in statusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="逻辑删除" prop="deleted">
              <el-select
                v-model="dataForm.deleted"
                placeholder="请选择"
                style="width: 100%"
              >
                <el-option
                  v-for="item in deletedOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
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

    <!-- 用户角色设置界面 -->
    <el-dialog
      title="设置当前用户的角色"
      width="45%"
      :visible.sync="rolesVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        ref="rolesForm"
        @keyup.enter.native="submitUserRoles()"
        center
        :size="size"
        style="text-align: left"
      >
        <el-table
          ref="multipleTable"
          :data="rolesData"
          tooltip-effect="dark"
          style="width: 100%"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column prop="code" label="角色编码" min-width="50">
          </el-table-column>
          <el-table-column prop="name" label="角色名称" min-width="50">
          </el-table-column>
          <el-table-column prop="memo" label="备注" min-width="50">
          </el-table-column>
        </el-table>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="rolesVisible = false">{{
          $t("action.cancel")
        }}</el-button>
        <el-button
          :size="size"
          type="primary"
          @click="submitUserRoles()"
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
import PopupTreeInput from "@/components/PopupTreeInput";
import KtTable from "@/views/Core/KtTable";
import KtButton from "@/views/Core/KtButton";
import TableColumnFilterDialog from "@/views/Core/TableColumnFilterDialog";
import { format } from "@/utils/datetime";
export default {
  components: {
    PopupTreeInput,
    KtTable,
    KtButton,
    TableColumnFilterDialog,
  },
  data() {
    return {
      //1.首先是部门信息的处理
      sysDeptData: [], //部门信息数据
      sysDeptProps: {
        //映射关系
        label: "name",
        children: "children",
      },
      loading: false,
      findByUsername: true,
      size: "small",
      filters: {
        username: "",
        dept_id: "",
      },
      columns: [],
      pageRequest: {
        pageNum: 1,
        pageSize: 9,
        listCondition: [],
        listOrder: [],
      },
      pageResult: {},

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
      },
      statusOptions: [
        {
          value: 1,
          label: "正常",
        },
        {
          value: 2,
          label: "锁定",
        },
        {
          value: 3,
          label: "禁用",
        },
      ],

      deletedOptions: [
        {
          value: 0,
          label: "不删除",
        },
        {
          value: 1,
          label: "删除",
        },
      ],
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        username: "",
        nickname: "",
        password: "",
        dept_Id: 0,
        dept_id_name: "",
        email: "",
        mobile: "",
        avatar: "",
        status: 1,
        deleted: 0,
      },
      dialogDeptData: [],
      dialogDeptProps: {
        label: "name",
        children: "children",
      },
      roles: [],

      //以下是设置用户角色需要用到的数据
      user_id: 0,
      rolesVisible: false, //控制设置用户角色对话框的显示
      rolesData: [], //角色的所有信息列表
      rolesExisted: [], //已经存在的角色
      selection: {
        listCondition: [],
        listOrder: [],
      },
    };
  },
  watch: {
    rolesExisted: function () {
      this.$nextTick(() => {
        this.$refs.multipleTable.clearSelection(); //清除所有的选择项目
        if (this.rolesExisted != null && this.rolesData != null) {
          for (let i = 0; i < this.rolesExisted.length; i++) {
            for (let j = 0; j < this.rolesData.length; j++) {
              if (this.rolesExisted[i].id == this.rolesData[j].id) {
                this.$refs.multipleTable.toggleRowSelection(this.rolesData[i]);
                break;
              }
            }
          }
        }
      });
    }, //选择的相关信息,用户能够方便的看出来
  },
  methods: {
    //1.首先是列出部门信息的处理
    // (1)找到部门信息
    getSysDeptData: function () {
      this.loading = true;
      this.$api.dept
        .findDeptTree(false)
        .then((res) => {
          if (res.code == 200) {
            this.sysDeptData = res.data;
            this.dialogDeptData=res.data
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.sysDeptData = null;
             this.dialogDeptData=null;
          }

          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.sysDeptData = null;
           this.dialogDeptData=null;
          this.loading = false;
        });
    },
    //(2)结点点击后后的功能
    handleNodeClick(obj, node, data) {
      this.filters.dept_id = obj.id;
      this.findByUsername = false;
      this.findPage(1);
    },

    // 获取分页数据
    findPage: function (pageNum) {
      this.loading = true;
      if (pageNum != null) {
        this.pageRequest.pageNum = pageNum.pageNum;
      }
      this.pageRequest.listCondition = [];
      if (this.findByUsername) {
        this.pageRequest.listCondition = [
          {
            conditionLogic: "and",
            fieldOfJava: "username",
            fieldValue: this.filters.username,
            filedLogic: "lorm",
            newCondition: true,
          },
        ];
      } else {
        this.pageRequest.listCondition = [
          {
            conditionLogic: "and",
            fieldOfJava: "dept_id",
            fieldValue: this.filters.dept_id,
            filedLogic: "=",
            newCondition: true,
          },
        ];
      }

      this.$api.user
        .findPage(this.pageRequest)
        .then((res) => {
          if (res.code == 200) {
            this.pageResult = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.tip, type: "error" });
            this.pageResult = null;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.pageResult = {};
          this.loading = false;
        });
    },

    handleQuery: function () {
      this.findByUsername = true;
      this.filters.dept_id = "";
      this.findPage(1);
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true;
      this.operation = true;
      this.dataForm = {
        id: 0,
        name: "",
        password: "",
        dept_id: 0,
        dept_id_name: "",
        email: "",
        mobile: "",
        status: 1,
        deleted:0
      };
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true;
      this.operation = false;
      this.dataForm = Object.assign({}, params.row);
      
    },

    // 显示编辑界面
    handleDelete: function (paramsIn) {
      let params = Object.assign({}, paramsIn.row);
      this.$confirm(
        "确认删除:用户名称为'" + params.username + "'的选中记录吗？",
        "提示",
        {
          type: "warning",
        }
      ).then(() => {
        this.$api.user
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
    // 显示用户角色界面
    handleRoles: function (params) {
      this.user_id=params.row.id;
      this.findUserRoles(params.row.id);
      this.rolesVisible = true;
    },

    // 编辑
    submitForm: function () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            let params = Object.assign({}, this.dataForm);
            this.$api.user.save(params).then((res) => {
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
    
    // 菜单树选中
    deptTreeCurrentChangeHandle(data, node) {
      this.dataForm.dept_id = data.id;
      this.dataForm.dept_id_name = data.name;
    },

    // 处理表格列过滤显示
    initColumns: function () {
      this.columns = [
        { prop: "dept_id_name", label: "所属部门", minWidth: 50 },
        { prop: "username", label: "用户名称", minWidth: 50 },
        { prop: "nickname", label: "用户昵称", minWidth: 50 },
        { prop: "roles", label: "角色", minWidth: 100 },
      ];
    },

    //以下是设置用户角色的相关功能
    //获取用户目前的角色信息
    findUserRoles: function (user_id) {
      this.$api.user
        .findUserRoles(user_id)
        .then((res) => {
          if (res.code == 200) {
            this.rolesExisted = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.tip, type: "error" });
            this.rolesExisted = null;
          }
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.rolesExisted = {};
        });
    },

    //获取所有的角色信息
    findRoles: function () {
      this.$api.role
        .find(this.selection)
        .then((res) => {
          if (res.code == 200) {
            this.rolesData = res.data;
          } else {
            this.$message({ message: "操作失败1, " + res.tip, type: "error" });
            this.rolesData = null;
          }
        })
        .catch((err) => {
          this.$message({
            message: "操作失败2, " + err.message,
            type: "error",
          });
          this.rolesData = null;
        });
    },

    //设置当前用户的角色提交按钮的功能
    submitUserRoles() {
      this.editLoading = true;
      let roleSelection = this.$refs.multipleTable.selection;
      let role_ids = [];
      if (roleSelection != null) {
        for (let i = 0; i < roleSelection.length; i++) {
          role_ids.push(roleSelection[i].id);
        }
      }

      let params = {
        user_id: this.user_id,
        role_ids: role_ids,
      };
      
      this.$api.user
        .saveRoles(params)
        .then((res) => {
          if (res.code == 200) {
            this.$message({ message: "操作成功", type: "success" });
            this.rolesVisible = false;
            this.$refs["rolesForm"].resetFields();
          } else {
            this.$message({
              message: "操作失败, " + res.tip,
              type: "error",
            });
            this.rolesVisible = false;
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
    this.getSysDeptData();
    this.findRoles();
  },
};
</script>

<style scoped>
.el-aside {
  border-color: rgba(200, 206, 206, 0.5);
  border-right-width: 1px;
  border-right-style: solid;
  height: calc(100vh - 70px);
}
.aside-header {
  padding-left: 8px;
  padding-bottom: 5px;
  text-align: center;
  font-size: 16px;
  color: rgb(20, 89, 121);
}
.el-main {
  margin-top: 0px;
  padding-top: 0px;
  height: 100%;
}
</style>