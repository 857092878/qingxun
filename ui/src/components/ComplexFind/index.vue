<template>
  <!--复杂查询条件设置界面 -->
  <el-dialog
    title="多查询条件设置"
    width="60%"
    top="80px"
    :visible.sync="isVisible_"
    :close-on-click-modal="false"
  >
    <el-form
      :model="dataForm"
      ref="dataForm"
      center
      label-width="90px"
      :size="size"
      style="text-align: left"
    >
      <div>
        <el-table
          :data="tableData"
          ref="singleTable"
          stripe
          :size="size"
          height="300px"
          border
          :element-loading-text="$t('action.loading')"
          style="width: 100%"
          :row-style="{ height: 1 + 'px' }"
          :cell-style="{ padding: 3 + 'px' }"
          :highlight-current-row="true"
          @current-change="handleCurrentChange"
        >
          <el-table-column prop="seq" align="center" label="序号" width="50px">
          </el-table-column>
          <el-table-column prop="name" align="left" label="查询逻辑条件">
          </el-table-column>

          <el-table-column
            fixed="right"
            align="center"
            width="100"
            :label="$t('action.operation')"
          >
            <template slot-scope="scope">
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
      </div>
      <el-row>
        <el-col :span="7">
          <el-form-item label="当前选择行" prop="choice">
            <el-input v-model="dataForm.nowIndex" readonly="" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="新的条件" prop="newCondition">
            <el-select
              v-model="dataForm.newCon"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in optionsNewCon"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="条件间逻辑" prop="value">
            <el-select
              v-model="dataForm.conLogic"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in optionsConLogic"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="7">
          <el-form-item label="查询字段" prop="logic">
            <el-select
              v-model="dataForm.fldName"
              placeholder="请选择"
              style="width: 100%"
              @change="fldOfJavaChange"
            >
              <el-option
                v-for="item in columns"
                :key="item.name"
                :label="item.nameCHN"
                :value="item.name"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="查询逻辑" prop="logic">
            <el-select
              v-model="dataForm.fldLogic"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in optionsNow"
                :key="item.logic"
                :label="item.logicCHN"
                :value="item.logic"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="查询数据" prop="value">
            <el-input v-model="dataForm.fldValue" placeholder="数据"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="3" :offset="1">
          <el-button size="small" type="primary" @click.native="conInsert"
            >新增</el-button
          >
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button :size="size" @click="handClose()">{{
        $t("action.cancel")
      }}</el-button>
      <el-button
        :size="size"
        type="primary"
        v-loading.fullscreen.lock="editLoading"
        element-loading-text="数据保存中，请稍候……"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        @click="handSubmit()"
        >{{ $t("action.comfirm") }}</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import KtButton from "@/views/Core/KtButton";
export default {
  components: {
    KtButton,
  },
  name: "ComplexFind",
  props: {
    columns: {
      type: Array,
      default: () => [
        {
          name: "name",
          nameCHN: "名称",
          isString: true,
        },
        {
          name: "chnName",
          nameCHN: "中文名称",
          isString: true,
        },
        {
          name: "prefix",
          nameCHN: "前缀",
          isString: true,
        },
        {
          name: "sortNumber",
          nameCHN: "排序",
          isString: false,
        },
        {
          name: "memo",
          nameCHN: "备注",
          isString: true,
        },
      ],
    },
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
      //逻辑条件（字符串）
      optionsString: [
        {
          logic: "LM",
          logicCHN: "左匹配",
        },
        {
          logic: "RM",
          logicCHN: "右匹配",
        },
        {
          logic: "LORM",
          logicCHN: "匹配",
        },
        {
          logic: "=",
          logicCHN: "等于",
        },
        {
          logic: "<>",
          logicCHN: "不等于",
        },
        {
          logic: ">",
          logicCHN: "大于",
        },
        {
          logic: ">=",
          logicCHN: "大于等于",
        },
        {
          logic: "<=",
          logicCHN: "小于等于",
        },
        {
          logic: "<",
          logicCHN: "小于",
        },
      ],
      //逻辑条件（非字符串)
      optionsOther: [
        {
          logic: "=",
          logicCHN: "等于",
        },
        {
          logic: "<>",
          logicCHN: "不等于",
        },
        {
          logic: ">",
          logicCHN: "大于",
        },
        {
          logic: ">=",
          logicCHN: "大于等于",
        },
        {
          logic: "<=",
          logicCHN: "小于等于",
        },
        {
          logic: "<",
          logicCHN: "小于",
        },
      ],

      optionsNow: [],

      optionsNewCon: [
        {
          key: true,
          value: "是",
        },
        {
          key: false,
          value: "否",
        },
      ],

      optionsConLogic: [
        {
          key: "AND",
          value: "并且",
        },
        {
          key: "OR",
          value: "或者",
        },
      ],

      dataForm: {
        nowIndex: -1, //当前选择的行
        fldName: "", //查询字段名
        fldLogic: "", //查询逻辑
        fldValue: "", //查询值
        newCon: true, //新条件
        conLogic: "AND", //条件之间的逻辑
      },
      //查询条件
      listCondition: [],
      insertCon: true, //定义当前是新增行，还是旧行添加数据
      tableData: [], //表格数据
      editLoading: false,
    };
  },
  methods: {
    // 对话框的显示与隐藏
    handClose: function () {
      this.isVisible_ = false;
    },
    handSubmit: function () {
      if (this.listCondition.length > 0) {
        this.isVisible_ = false;
        this.$emit("handSubmit", {
          listCondition: this.listCondition,
        });
      } else {
        this.$message({
          message: "未设定查询条件，不能继续！",
          type: "warning",
        });
        return;
      }
    },

    //以下是条件的各种操作
    fldOfJavaChange(value) {
      if (value != "") {
        this.dataForm.fldLogic = "";
        if (this.getFldType(value)) {
          this.optionsNow = this.optionsString;
        } else {
          this.optionsNow = this.optionsOther;
        }
      }
    },

    handleCurrentChange(currentRow, oldCurrentRow) {
      if (currentRow != null) {
        this.dataForm.nowIndex = currentRow.seq;
      } else {
        this.dataForm.nowIndex = -1;
      }
    },
    handleDelete(paramsIn) {
      let iDelete = paramsIn.seq;
      let iEnd; //前半段数组的结束
      let iStart; //后半段数组的开始
      let iNow = 0; //目前循环的位置
      let iTrue; //真正的列表位置

      for (let i = 0; i < this.listCondition.length; i++) {
        if (this.listCondition[i].newCondition) {
          iNow++; //如果是新条件，则增加
        }
        if (iNow == iDelete) {
          iTrue = i;
          break;
        }
      }

      iEnd = iTrue - 1;
      iStart = -1; //如果删除最后一个，则iStart为-1
      for (let i = iTrue + 1; i < this.listCondition.length; i++) {
        if (this.listCondition[i].newCondition) {
          iStart = i;
          break;
        }
      }

      let listNew = [];
      let listPre = [];
      let listLast = [];
      if (iEnd > -1) {
        listPre = this.listCondition.slice(0, iEnd + 1);
      }
      if (iStart > -1) {
        listLast = this.listCondition.slice(iStart);
      }
      listNew = listPre.concat(listLast);
      this.listCondition = listNew;
    },

    //增加条件
    conInsert: function () {
      if (this.dataForm.fldName == "") {
        this.$message({ message: "必须选择一个查询字段！", type: "error" });
        return;
      }
      if (this.dataForm.fldLogic == "") {
        this.$message({ message: "必须选择一个查询逻辑！", type: "error" });
        return;
      }
      if (this.dataForm.fldValue == "") {
        this.$message({
          message: "必须输入当前查询字段的查询数据！",
          type: "error",
        });
        return;
      }
      if (this.dataForm.newCon) {
        //新条件，直接增加到最末尾
        this.conInsertNew();
      } else {
        //旧条件，放到其它条件数据里
        this.conInsertAdd();
      }
    },
    conInsertNew: function () {
      this.insertCon = true;
      this.listCondition.push({
        newCondition: this.dataForm.newCon,
        conditionLogic: this.dataForm.conLogic,
        fieldOfJava: this.dataForm.fldName,
        filedLogic: this.dataForm.fldLogic,
        fieldValue: this.dataForm.fldValue,
      });
    },
    conInsertAdd: function () {
      if (this.dataForm.nowIndex == -1) {
        this.$message({
          message: "当前没有选中任何行，不能继续！",
          type: "error",
        });
        return;
      }

      let iAdd = this.dataForm.nowIndex;
      let iNext; //并入到这行的末尾，这个找的是下一行的开始
      let iNow = 0; //目前循环的位置
      let iTrue; //找出的要添加到那一行的开始位置，必然可以找到

      for (let i = 0; i < this.listCondition.length; i++) {
        if (this.listCondition[i].newCondition) {
          iNow++; //如果是新条件，则增加
        }
        if (iNow == iAdd) {
          iTrue = i;
          break;
        }
      }

      iNext = -1; //如果没有找到，则证明是在最后一行添加，所以为-1
      for (let i = iTrue + 1; i < this.listCondition.length; i++) {
        //从下一个列表开始，找到为true的就停下
        if (this.listCondition[i].newCondition) {
          iNext = i;
          break;
        }
      }

      let listNew = [];
      let listPre = [];
      let listLast = [];
      if (iNext != -1) {
        listPre = this.listCondition.slice(0, iNext);
        listPre.push({
          newCondition: this.dataForm.newCon,
          conditionLogic: this.dataForm.conLogic,
          fieldOfJava: this.dataForm.fldName,
          filedLogic: this.dataForm.fldLogic,
          fieldValue: this.dataForm.fldValue,
        });
        listLast = this.listCondition.slice(iNext);
        listNew = listPre.concat(listLast);
        this.listCondition = listNew;
      } else {
        this.listCondition.push({
          newCondition: this.dataForm.newCon,
          conditionLogic: this.dataForm.conLogic,
          fieldOfJava: this.dataForm.fldName,
          filedLogic: this.dataForm.fldLogic,
          fieldValue: this.dataForm.fldValue,
        });
      }
      this.insertCon = false;
    },

    //通过fldName返回isString
    getFldType(params) {
      for (let i = 0; i < this.columns.length; i++) {
        if (params == this.columns[i].name) {
          return this.columns[i].isString;
        }
      }
      return true;
    },

    //通过fldName返回field
    getField(params) {
      for (let i = 0; i < this.columns.length; i++) {
        if (params == this.columns[i].name) {
          return this.columns[i];
        }
      }
      return null;
    },

    //根据fldLogic返回fldLogicCHN
    getFldLogicCHN(params) {
      for (let i = 0; i < this.optionsString.length; i++) {
        if (params == this.optionsString[i].logic) {
          return this.optionsString[i].logicCHN;
        }
      }
      return "null";
    },

    //根据fldLogic返回fldLogicCHN
    getConLogicCHN(params) {
      for (let i = 0; i < this.optionsConLogic.length; i++) {
        if (params == this.optionsConLogic[i].key) {
          return this.optionsConLogic[i].value;
        }
      }
      return "null";
    },
  },

  watch: {
    listCondition: function () {
      let len = this.listCondition.length;
      this.tableData = [];
      if (len == 0) return;
      let indexNow = 1;

      let strCHN = "";
      let objNow = this.listCondition[0];
      strCHN =
        "( " +
        this.getField(objNow.fieldOfJava).nameCHN +
        " " +
        this.getFldLogicCHN(objNow.filedLogic) +
        " " +
        objNow.fieldValue +
        " )";
      for (let i = 1; i < this.listCondition.length; i++) {
        objNow = this.listCondition[i];
        if (objNow.newCondition) {
          this.tableData.push({
            seq: indexNow,
            name: strCHN,
          });
          indexNow++;
          strCHN =
            "( " +
            this.getField(objNow.fieldOfJava).nameCHN +
            " " +
            this.getFldLogicCHN(objNow.filedLogic) +
            " " +
            objNow.fieldValue +
            " )";
        } else {
          strCHN =
            strCHN +
            "  " +
            this.getConLogicCHN(objNow.conditionLogic) +
            "  ( " +
            this.getField(objNow.fieldOfJava).nameCHN +
            " " +
            this.getFldLogicCHN(objNow.filedLogic) +
            " " +
            objNow.fieldValue +
            " )";
        }
      }
      this.tableData.push({
        seq: indexNow,
        name: strCHN,
      });
      if (this.insertCon) {
        this.$refs.singleTable.setCurrentRow(
          this.tableData[this.tableData.length - 1]
        );
      } else {
        this.$refs.singleTable.setCurrentRow(
          this.tableData[this.dataForm.nowIndex - 1]
        );
      }
    },
    isVisible: function () {
      if (this.isVisible) {
        this.$nextTick(() => {
          this.$refs.singleTable.doLayout();
          // table加ref="singleTable"
        });
      }
    },
  },
  mounted() {},

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
.el-table {
  margin: 0 auto;
  padding: 0;
}
.el-table th.gutter {
  display: table-cell !important;
  width: 5px !important;
}

.el-table__fixed-body-wrapper .el-table__body {
  padding-bottom: 8px !important;
}
</style>