<template>
  <div>
    <!-- 学生选择功能-->
    <el-dialog
      title="选择学生"
      width="68%"
      top="40px"
      :visible.sync="isVisible_"
      :close-on-click-modal="false"
      append-to-body
    >
      <div>
        <!--主要是查询学生出来，然后双节选择或确认选择-->
        <div
          class="toolbar"
          style="float: left; padding-top: 10px; padding-left: 20px"
        >
          <el-form :inline="true" :model="filters" :size="size">
            <el-form-item>
              <el-input v-model="filters.code" placeholder="学号"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input v-model="filters.nameAll" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="filters.classCode_code"
                placeholder="班级编号"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <kt-button
                icon="fa fa-refresh"
                :label="$t('action.search')"
                perms="StuPhoto:view"
                type="primary"
                @click="handleQuery"
              />
            </el-form-item>
          </el-form>
        </div>

        <!--表格内容栏-->
        <el-table
          :data="tableData"
          stripe
          height="380px"
          size="mini"
          style="width: 98%"
          border
          :row-style="{ height: 1 + 'px' }"
          :cell-style="{ padding: 3 + 'px' }"
          :highlight-current-row="true"
          v-loading="loading"
          :element-loading-text="$t('action.loading')"
        >
          >
          <el-table-column prop="seq" align="center" label="序号" width="50px">
          </el-table-column>
          <el-table-column
            prop="classCode_code"
            align="center"
            label="班级编号"
          ></el-table-column>
          <el-table-column
            prop="code"
            align="center"
            label="学号"
          ></el-table-column>
          <el-table-column
            prop="nameAll"
            align="center"
            label="姓名"
          ></el-table-column>
          <el-table-column
            fixed="right"
            align="center"
            width="100"
            :label="$t('action.operation')"
          >
            <template slot-scope="scope">
              <kt-button
                icon="fa fa-edit"
                label="选择"
                perms="StuCountry:edit"
                @click="handleChoice(scope.row)"
              />
            </template>
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="isVisible_ = false">返回不选</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import KtButton from "@/views/Core/KtButton";

export default {
  name: "SelectStudent",
  components: {
    KtButton,
  },
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },

    size: {
      type: String,
      default: "small",
    },
  },
  data() {
    return {
      tableData: [],
      loading: false,
      queryConditions: {
        listCondition: [],
        listOrder: [],
      },

      filters: {
        classCode_code: "",
        code: "",
        nameAll: "",
      },
    };
  },
  methods: {
    findStudent: function () {
      this.loading = true;
      this.$api.StuBasic.find(this.queryConditions)
        .then((res) => {
          if (res.code == 200) {
            this.tableData = res.data;
          } else {
            this.$message({ message: "操作失败, " + res.msg, type: "error" });
            this.tableData = null;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.$message({ message: "操作失败, " + err.message, type: "error" });
          this.tableData = null;
          this.loading = false;
        });
    },

    handleQuery: function () {
      if (
        this.filters.classCode_code == "" &&
        this.filters.code == "" &&
        this.filters.nameAll == ""
      ) {
        this.$message({
          message: "必须输入班级编号、学号或姓名中的一个值才能继续！",
          type: "error",
        });
        return;
      }
      this.queryConditions.listCondition = [];
      if (this.filters.classCode_code != "") {
        this.queryConditions.listCondition.push({
          conditionLogic: "and",
          fieldOfJava: "classCode_code",
          fieldValue: this.filters.classCode_code,
          filedLogic: "=",
          newCondition: true,
        });
      }

      if (this.filters.code != "") {
        this.queryConditions.listCondition.push({
          conditionLogic: "and",
          fieldOfJava: "code",
          fieldValue: this.filters.code,
          filedLogic: "LM",
          newCondition: true,
        });
      }
      if (this.filters.nameAll != "") {
        this.queryConditions.listCondition.push({
          conditionLogic: "and",
          fieldOfJava: "nameAll",
          fieldValue: this.filters.nameAll,
          filedLogic: "LM",
          newCondition: true,
        });
      }
      this.findStudent();
    },

    handleChoice(row) {
      this.$emit("handSubmit",row);
      this.isVisible_=false;
    },
  },

  mounted() {
  },

  computed: {
    isVisible_: {
      get() {
        return this.isVisible;
      },
      set(val) {
        //isVisible_改变由父组件控制
        this.$emit("changeVisible", val);
      },
    },
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

