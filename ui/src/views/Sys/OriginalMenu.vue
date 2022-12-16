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
      <el-table-column prop="name" header-align="center" label="功能名称">
      </el-table-column>
      <el-table-column
        prop="code"
        header-align="center"
        width="80"
        label="功能编码"
      >
      </el-table-column>

      <el-table-column
        prop="authority"
        header-align="center"
     
        label="功能授权"
      >
      </el-table-column>
      <el-table-column prop="memo" header-align="center" label="备注">
      </el-table-column>

      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="185"
        :label="$t('action.operation')"
      >
        <template slot-scope="scope">
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
      :title="!dataForm.id ? '功能管理--新增' : '功能管理--修改'"
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
       <el-form-item label="功能编码" prop="code">
          <el-input v-model="dataForm.code" placeholder="功能编码"></el-input>
        </el-form-item>
        <el-form-item label="功能名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="功能名称"></el-input>
        </el-form-item>
        <el-form-item label="功能授权" prop="authority">
          <el-input v-model="dataForm.authority" placeholder="功能授权"></el-input>
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
        authority: "",
        memo: "",
      },
      dataRule: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        code: [{ required: true, message: "编码不能为空", trigger: "blur" }],
      },
      editLoading: false,
    };
  },
  methods: {
    // 获取数据
    findTreeData: function () {
      this.loading = true;
      this.$api.func
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
        authority: "",
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
        this.$api.func
          .deleteById(params.id)
          .then((res) => {
            if (res.code == 200) {
              this.findTree();
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
          this.$api.func
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
    // 时间格式化
    dateFormat: function (row, column, cellValue, index) {
      return format(row[column.property]);
    },
  },
  mounted() {
    if (this.firstLoad) {
      // this.findTreeData()
      this.firstLoad = false;
    }
  },
};
</script>

<style scoped>
</style>