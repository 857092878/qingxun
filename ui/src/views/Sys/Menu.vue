<template>
  <div class="page-container">
    <!--工具栏-->
    <div
      class="toolbar"
      style="float: left; padding-top: 10px; padding-left: 15px"
    >
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <kt-button
            icon="fa fa-refresh"
            :label="$t('action.refresh')"
            perms="sys:dept:view"
            type="primary"
            @click="findTreeData()"
          />
        </el-form-item>
        <el-form-item>
          <kt-button
            icon="fa fa-plus"
            :label="$t('action.add')"
            perms="sys:dept:add"
            type="primary"
            @click="handleAdd"
          />
        </el-form-item>
      </el-form>
    </div>
    <!--表格树内容栏-->
    <el-table
      :data="tableTreeDdata"
      stripe
      size="mini"
      style="width: 100%"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      border
      v-loading="loading"
      rowKey="id"
      :element-loading-text="$t('action.loading')"
    >
      <el-table-column prop="name" header-align="center" label="菜单名称">
      </el-table-column>
      <el-table-column
        prop="code"
        header-align="center"
        label="菜单编码"
        width="70"
      >
      </el-table-column>
      <el-table-column
        prop="icon"
        header-align="center"
        label="菜单图标"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          <i :class="scope.row.icon || ''"></i>
        </template>
      </el-table-column>
      <el-table-column prop="url" header-align="center" label="菜单Url">
      </el-table-column>
      <el-table-column prop="functions" header-align="center" label="菜单功能">
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        label="菜单类型"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 0" size="small">目录</el-tag>
          <el-tag v-else-if="scope.row.type === 1" size="small">按钮</el-tag>
          <el-tag v-else-if="scope.row.type === 2" size="small">链接</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="270"
        :label="$t('action.operation')"
      >
        <template slot-scope="scope">
          <kt-button
            icon="el-icon-orange"
            label="功能"
            perms="sys:dept:edit"
            @click="handleFunctions(scope.row)"
          />
          <kt-button
            icon="fa fa-edit"
            :label="$t('action.edit')"
            perms="sys:dept:edit"
            @click="handleEdit(scope.row)"
          />
          <kt-button
            icon="fa fa-trash"
            :label="$t('action.delete')"
            perms="sys:dept:delete"
            type="danger"
            @click="handleDelete(scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改界面 -->
    <el-dialog
      :title="!dataForm.id ? '菜单管理--新增' : '菜单管理--修改'"
      width="45%"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        :rules="dataRule"
        ref="dataForm"
        @keyup.enter.native="submitForm()"
        center
        label-width="80px"
        :size="size"
        style="text-align: left"
      >
        <el-form-item label="菜单编码" prop="code">
          <el-input v-model="dataForm.code" placeholder="菜单编码"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-input v-model="dataForm.icon" placeholder="菜单图标"></el-input>
        </el-form-item>
        <el-form-item label="菜单Url" prop="url">
          <el-input v-model="dataForm.url" placeholder="菜单Url"></el-input>
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-select v-model="dataForm.type" placeholder="请选择">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input v-model="dataForm.memo" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="dialogVisible = false">{{
          $t("action.cancel")
        }}</el-button>
        <el-button
          :size="size"
          type="primary"
          @click="submitForm()"
          v-loading.fullscreen.lock="editLoading"
          element-loading-text="数据保存中，请稍候……"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          >{{ $t("action.comfirm") }}</el-button
        >
      </span>
    </el-dialog>

    <!-- 菜单功能设置界面 -->
    <el-dialog
      title="设置当前菜单的功能"
      width="45%"
      :visible.sync="functionsVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        ref="funcForm"
        @keyup.enter.native="submitMenuFunction()"
        center
        :size="size"
        style="text-align: left"
      >
        <el-tree
          :data="sysFuncData"
          :props="sysFuncProps"
          show-checkbox
          default-expand-all
          node-key="id"
          ref="tree"
          highlight-current
          :accordion="true"
          :check-strictly="true"
          @check-change="setChecked"
        >
        </el-tree>

        <el-form-item label="当前选择：">
          <span>{{ checkedInfo }}</span>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="functionsVisible = false">{{
          $t("action.cancel")
        }}</el-button>
        <el-button
          :size="size"
          type="primary"
          @click="submitMenuFunction()"
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
import KtButton from "@/views/Core/KtButton";
export default {
  components: {
    KtButton,
  },
  data() {
    return {
      size: "small",
      loading: false,
      filters: {
        name: "",
      },
      tableTreeDdata: [],
      dialogVisible: false,
      dataForm: {
        id: 0,
        name: "",
        code: "",
        icon: "",
        url: "",
        type: 0,
        memo: "",
      },
      dataRule: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        code: [{ required: true, message: "编码不能为空", trigger: "blur" }],
      },
      typeOptions: [
        {
          value: 0,
          label: "目录",
        },
        {
          value: 1,
          label: "按钮",
        },
         {
          value: 2,
          label: "链接",
        },
      ],
      editLoading: false,
      functionsVisible: false,
      sysFuncData: [], //功能结构树
      sysFuncProps: {
        //映射关系
        label: "name",
        children: "children",
        disabled: "type",
      },
     
        menuId: "0", //当前选项的菜单Id
        funcIdExisted: [], //给出当前菜单已经选择了的funcId
        checkedNode: ["sys:user:add"], //当前选择的功能结点（存放权限）
     
      selCondition: {
        listCondition: [],
        listOrder: [],
      },
      selResult: true,
    };
  },
  computed: {
    checkedInfo: function () {
      let sr = "";
      let dataNow = this.checkedNode;
      if (dataNow.length > 0) {
        sr += dataNow[0];
        for (let i = 1; i < dataNow.length; i++) {
          sr += "," + dataNow[i];
        }
      }
      return sr;
    }, //选择的相关信息,用户能够方便的看出来
  },
  watch: {
    funcIdExisted: function () {
       this.$nextTick(() => {
         // this.$refs.tree.setCheckedKeys([]);
          this.$refs.tree.setCheckedKeys(this.funcIdExisted);
        });
    }, //选择的相关信息,用户能够方便的看出来
  },
  methods: {
    // 获取数据
    findTreeData: function () {
      this.loading = true;
      this.$api.menu
        .findTree()
        .then((res) => {
          if (res.code == 200) {
            this.tableTreeDdata = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.tableTreeDdata = null;
          }

          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.tableTreeDdata = null;
          this.loading = false;
        });
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true;
      this.dataForm = {
        id: 0,
        name: "",
        code: "",
        icon: "",
        url: "",
        type: 0,
        memo: "",
      };
    },
    // 显示编辑界面
    handleEdit: function (row) {
      this.dialogVisible = true;
      Object.assign(this.dataForm, row);
    },
    // 删除
    handleDelete(row) {
      let params = Object.assign({}, row);
      this.$confirm(
        "确认删除:名称为'" + params.name + "'的选中记录吗？",
        "提示",
        {
          type: "warning",
        }
      ).then(() => {
        this.$api.menu
          .deleteById(params.id)
          .then((res) => {
            if (res.code == 200) {
              this.findTreeData();
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

    // 表单提交
    submitForm() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.editLoading = true;
          let params = Object.assign({}, this.dataForm);
          this.$api.menu
            .save(params)
            .then((res) => {
              if (res.code == 200) {
                this.$message({ message: "操作成功", type: "success" });
                this.dialogVisible = false;
                this.$refs["dataForm"].resetFields();
              } else {
                this.$message({
                  message: "操作失败, " + res.tip,
                  type: "error",
                });
              }
              this.findTreeData();
              this.editLoading = false;
            })
            .catch((err) => {
              this.$message({
                message: "操作失败, " + err.message,
                type: "error",
              });
              this.editLoading = false;
            });
        }
      });
    },

    // 显示功能设置界面
    handleFunctions: function (row) {
      let params = Object.assign({}, row);
      this.menuId = params.id;
      this.getExistedFunc(params.id);
      if (this.selResult) {
        this.functionsVisible = true;
       
      }
    },

    // 表单提交
    submitFunctions() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.editLoading = true;
          let params = Object.assign({}, this.dataForm);
          this.$api.menu
            .save(params)
            .then((res) => {
              if (res.code == 200) {
                this.$message({ message: "操作成功", type: "success" });
                this.dialogVisible = false;
                this.$refs["dataForm"].resetFields();
              } else {
                this.$message({
                  message: "操作失败, " + res.tip,
                  type: "error",
                });
              }
              this.findTreeData();
              this.editLoading = false;
            })
            .catch((err) => {
              this.$message({
                message: "操作失败, " + err.message,
                type: "error",
              });
              this.editLoading = false;
            });
        }
      });
    },

    //以下是菜单功能的相关
    //获取所有的功能
    getSysFuncData: function () {
      this.loading = true;
      this.$api.func
        .findTree()
        .then((res) => {
          if (res.code == 200) {
            this.sysFuncData = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.sysFuncData = null;
          }

          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.sysFuncData = null;
          this.loading = false;
        });
    },

    //获取当前菜单已经存在的功能选项
    getExistedFunc: function (menu_id) {
      this.selCondition.listCondition = [
        {
          conditionLogic: "and",
          fieldOfJava: "menu_id",
          fieldValue: menu_id,
          filedLogic: "=",
          newCondition: true,
        },
      ];

      this.$api.menu
        .getExistedFuncs(this.selCondition)
        .then((res) => {
          if (res.code == 200) {
            //设置两个数据
            let checkedData = res.data;
            let checkAuthority = [];
            let checkFuncId = [];
            for (let i = 0; i < checkedData.length; i++) {
              checkAuthority[i] = checkedData[i].func_id_authority;
              checkFuncId[i] = checkedData[i].func_id;
            }
            this.checkedNode = checkAuthority;
            this.funcIdExisted = checkFuncId;
            this.selResult = true;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.selResult = false;
          }
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.selResult = false;
        });
    },

    //当节点选择改变时，调用的事件
    setChecked: function () {
      let checkedData = this.$refs.tree.getCheckedNodes();
      let checkAuthority = [];
      let checkFuncId = [];
      for (let i = 0; i < checkedData.length; i++) {
        checkAuthority[i] = checkedData[i].authority;
        checkFuncId[i] = checkedData[i].id;
      }
      this.checkedNode = checkAuthority;
      this.funcIdExisted = checkFuncId;
    },
    // 表单提交
    submitMenuFunction() {
      let checkedData = this.$refs.tree.getCheckedNodes();
      let funcId = [];
      for (let i = 0; i < checkedData.length; i++) {
        funcId[i] = checkedData[i].id;
      }

      let params = {
        menu_id: this.menuId,
        func_ids: this.funcIdExisted,
      };

      this.$api.menu
        .saveFunctions(params)
        .then((res) => {
          if (res.code == 200) {
            this.$message({ message: "操作成功", type: "success" });
            this.functionsVisible = false;
            this.$refs["funcForm"].resetFields();
          } else {
            this.$message({
              message: "操作失败, " + res.tip,
              type: "error",
            });
          }
          this.findTreeData();
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
    this.getSysFuncData();
       this.findTreeData();
  },
};
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}

.checked-header {
  padding-left: 8px;
  padding-bottom: 5px;
  text-align: center;
  font-size: 16px;
  color: rgb(20, 89, 121);
}
</style>