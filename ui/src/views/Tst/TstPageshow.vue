<template>
  <div class="page-container">
    <div class="data-list">
      <!--工具栏-->
      <div
        class="toolbar"
        style="float: left; padding-top: 10px; padding-left: 20px"
      >
        <el-form :inline="true" :model="filters" :size="size">
          <el-form-item>
            <el-input v-model="filters.fname" placeholder="姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <kt-button
              icon="fa fa-refresh"
              :label="$t('action.search')"
              perms="TstPageshow:view"
              type="primary"
              @click="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <kt-button
              icon="fa fa-refresh"
              :label="$t('action.complexSearch')"
              perms="TstPageshow:view"
              type="primary"
              @click="handleQueryComplex"
            />
          </el-form-item>
          <el-form-item>
            <kt-button
              icon="fa fa-plus"
              :label="$t('action.add')"
              perms="TstPageshow:add"
              type="primary"
              @click="handleAdd"
            />
          </el-form-item>
        </el-form>
      </div>

      <!--表格内容栏-->
      <kt-table
        permsEdit="TstPageshow:edit"
        permsDelete="TstPageshow:delete"
        permsThird="TstPageshow:thrird"
        :showThird="false"
        thirdLabel="菜单"
        :width="185"
        :loading="loading"
        :data="pageResult"
        :columns="columns"
        @findPage="findPage"
        @handleEdit="handleEdit"
        @handleDelete="handleDelete"
        @handleThird="handleThird"
        @handleCurrentChange="handleCurrentChange"
      >
      </kt-table>
    </div>
    <!-- 新增修改界面 -->
    <el-dialog
      :title="!dataForm.id ? '分页显示信息--新增' : '分页显示信息--修改'"
      width="50%"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="dataForm"
        :rules="dataRules"
        ref="dataForm"
        @keyup.enter.native="submitForm()"
        center
        label-width="80px"
        :size="size"
        style="text-align: left"
      >
        <el-form-item label="部门Id" prop="deptId">
          <el-input v-model="dataForm.deptId" placeholder="部门Id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="fname">
          <el-input v-model="dataForm.fname" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="fgender">
          <el-select
            v-model="dataForm.fgender"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in optionsFgender"
              :key="item.data"
              :label="item.show"
              :value="item.data"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="fage">
          <el-input v-model="dataForm.fage" placeholder="年龄"></el-input>
        </el-form-item>
        <el-form-item label="民族" prop="fnation">
          <el-select
            v-model="dataForm.fnation"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in optionsFnation"
              :key="item.data"
              :label="item.show"
              :value="item.data"
            ></el-option>
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
          v-loading.fullscreen.lock="editLoading"
          element-loading-text="数据保存中，请稍候……"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          @click="submit()"
          >{{ $t("action.comfirm") }}</el-button
        >
      </span>
    </el-dialog>

    <!-- 复杂查询界面 -->
    <complex-find
      :isVisible="complexVisible"
      :size="size"
      :columns="queryColumns"
      @handSubmit="complexSubmit"
      @changeVisible="complexVisibleChange"
    ></complex-find>
  </div>
</template>
<script>
import KtTable from "@/views/Core/KtTable";
import KtButton from "@/views/Core/KtButton";
import ComplexFind from "@/components/ComplexFind";
export default {
  components: {
    KtTable,
    KtButton,
    ComplexFind,
  },
  data() {
    return {
      size: "small",
      loading: false,
      filters: { fname: "" },
      columns: [],
      pageRequest: {
        pageNum: 1,
        pageSize: 20,
        listCondition: [],
        listOrder: [],
      },
      pageResult: {},
      optionsFgender: [],
      optionsFnation: [],
      dialogVisible: false,
      dataForm: {
        id: 0,
        deptId: 0,
        deptId_fname: "",
        deptId_fcode: "",
        fname: "",
        fgender: "",
        fage: "",
        fnation: 0,
        fnation_fname: "",
        memo: "",
      },
      dataRules: {
        id: [{ required: true, message: "id不能为空！", trigger: "blur" }],
        deptId: [
          { required: true, message: "部门Id不能为空！", trigger: "blur" },
        ],
        fname: [
          { required: true, message: "姓名不能为空！", trigger: "blur" },
          { max: 20, message: "姓名最大长度为20个字符长度！", trigger: "blur" },
        ],
        fgender: [
          { required: true, message: "性别不能为空！", trigger: "blur" },
          {
            min: 1,
            max: 1,
            message: "性别必须为1个字符长度！",
            trigger: "blur",
          },
        ],
        fnation: [
          { required: true, message: "民族不能为空！", trigger: "blur" },
        ],
        memo: [
          { max: 50, message: "备注最大长度为50个字符长度！", trigger: "blur" },
        ],
      },
      editLoading: false,
      complexVisible: false,
      queryColumns: [
        { name: "deptId_fname", nameCHN: "部门Id", isString: true },
        { name: "deptId_fcode", nameCHN: "部门Id", isString: true },
        { name: "fname", nameCHN: "姓名", isString: true },
        { name: "fgender", nameCHN: "性别", isString: true },
        { name: "fage", nameCHN: "年龄", isString: true },
        { name: "fnation_fname", nameCHN: "民族", isString: true },
      ],
    };
  },
  methods: {
    findPage: function (pageNum) {
      this.loading = true;
      if (pageNum != null) {
        this.pageRequest.pageNum = pageNum.pageNum;
      }
      this.$api.TstPageshow.findPage(this.pageRequest)
        .then((res) => {
          if (res.code == 200) {
            this.pageResult = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.pageResult.content = null;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.pageResult.content = null;
          this.loading = false;
        });
    },

    findOptionsFgender: function () {
      this.$api.TstPageshow.findFgender()
        .then((res) => {
          if (res.code == 200) {
            this.optionsFgender = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.optionsFgender = null;
          }
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.optionsFgender = null;
        });
    },

    findOptionsFnation: function () {
      this.$api.TstAllshow.findChoice()
        .then((res) => {
          if (res.code == 200) {
            this.optionsFnation = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.optionsFnation = null;
          }
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.optionsFnation = null;
        });
    },

    handleQuery: function () {
      this.pageRequest.listCondition = [];
      if (!(this.filters.fname == "")) {
        this.pageRequest.listCondition = [
          {
            conditionLogic: "and",
            fieldOfJava: "fname",
            fieldValue: this.filters.fname,
            filedLogic: "lorm",
            newCondition: true,
          },
        ];
      }
      this.findPage(1);
    },

    handleQueryComplex: function () {
      this.complexVisible = true;
    },

    handleAdd: function () {
      this.dialogVisible = true;
      this.dataForm = {
        id: 0,
        deptId: 0,
        deptId_fname: "",
        deptId_fcode: "",
        fname: "",
        fgender: "",
        fage: "",
        fnation: 0,
        fnation_fname: "",
        memo: "",
      };
      if (this.optionsFgender != null && this.optionsFgender.length > 0) {
        this.dataForm.fgender = this.optionsFgender[0].data;
      }

      if (this.optionsFnation != null && this.optionsFnation.length > 0) {
        this.dataForm.fnation = this.optionsFnation[0].data;
      }
    },

    handleEdit: function (params) {
      this.dialogVisible = true;
      this.operation = false;
      this.dataForm = Object.assign({}, params.row);
      this.dataForm.fgender = this.dataForm.fgender + "";
      this.dataForm.fnation = this.dataForm.fnation + "";
    },

    handleDelete(paramsIn) {
      let params = Object.assign({}, paramsIn.row);
      this.$confirm(
        "确认删除:名称为'" + params.name + "'的选中记录吗？",
        "提示",
        { type: "warning" }
      )
        .then(() => {
          this.$api.TstPageshow.deleteById(params.id)
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
        })
        .catch(() => {});
    },

    handleThird() {},

    handleCurrentChange(params) {},

    submit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.editLoading = true;
          let params = Object.assign({}, this.dataForm);
          this.$api.TstPageshow.save(params)
            .then((res) => {
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
    initColumns: function () {
      this.columns = [
        { prop: "seq", label: "序号", width: 50 },
        { prop: "deptId", label: "部门Id", minWidth: 50 },
         { prop: "deptId_fname", label: "部门名称", minWidth: 50 },
        { prop: "fname", label: "姓名", minWidth: 50 },
        { prop: "fgender", label: "性别", minWidth: 50 },
        { prop: "fage", label: "年龄", minWidth: 50 },
        { prop: "fnation_fname", label: "民族", minWidth: 50 },
        { prop: "memo", label: "备注", minWidth: 50 },
      ];
    },
    complexVisibleChange(params) {
      this.complexVisible = params;
    },

    complexSubmit(params) {
      this.pageRequest.listCondition = params.listCondition;
      this.findPage(1);
    },
  },
  mounted() {
    this.initColumns();
    this.findPage(1);
    this.findOptionsFgender();
    this.findOptionsFnation();
  },
};
</script>

<style scoped>
.data-list {
  background: white;
  border-radius: 10px;
  height: calc(100vh - 125px);
}
.el-table {
  margin: 0 auto;
}
</style>

