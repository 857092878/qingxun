<template>
  <div>
    <!--表格栏-->
    <el-table
      :data="data.content"
      :highlight-current-row="highlightCurrentRow"
      @selection-change="selectionChange"
      @current-change="handleCurrentChange"
      v-loading="loading"
      :element-loading-text="$t('action.loading')"
      :border="border"
      :stripe="stripe"
      :show-overflow-tooltip="showOverflowTooltip"
      :max-height="maxHeight"
      :size="size"
      :align="align"
      style="width: 98%"
      :row-style="{ height: 1 + 'px' }"
      :cell-style="{ padding: 5 + 'px' }"
    >
      <el-table-column
        v-for="column in columns"
        header-align="center"
        align="center"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
        :min-width="column.minWidth"
        :fixed="column.fixed"
        :key="column.prop"
        :type="column.type"
        :formatter="column.formatter"
      >
      </el-table-column>
      <el-table-column
        :label="$t('action.operation')"
        :width="width"
        fixed="right"
        v-if="showOperation"
        header-align="center"
        align="center"
      >
        <template slot-scope="scope">
          <kt-button
            icon="fa fa-edit"
            :label="firstLabel"
            :perms="permsEdit"
            :size="size"
             v-if="showFirst"
            @click="handleEdit(scope.$index, scope.row)"
          />
          <kt-button
            icon="fa fa-trash"
            :label="secondLabel"
            :perms="permsDelete"
            :size="size"
            type="danger"
             v-if="showSecond"
            @click="handleDelete(scope.$index, scope.row)"
          />
           <kt-button
            icon="el-icon-orange"
            :label="thirdLabel"
            :perms="permsThird"
            :size="size"
            v-if="showThird"
            type="success"
            @click="handleThird(scope.$index, scope.row)"
          />
        </template>
      </el-table-column>
    </el-table>
    <!--分页栏-->
    <div class="toolbar" style="padding: 10px 30px">
      <el-pagination
        layout="total, prev, pager, next, jumper"
        @current-change="refreshPageRequest"
        background
        :current-page="data.pageNum"
        :page-size="data.pageSize"
        :total="data.totalSize"
        style="float: right"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import KtButton from "@/views/Core/KtButton";
export default {
  name: "KtTable",
  components: {
    KtButton,
  },
  props: {
    columns: Array, // 表格列配置
    data: Object, // 表格分页数据
    permsEdit: String, // 编辑权限标识
    permsDelete: String, // 删除权限标识
    permsThird: String, //第三个按钮的功能权限标识
    showFirst:{
      type:Boolean,
      default:true,
    },
    firstLabel: {
      // 第三个按钮的标题
      type: String,
      default: "编辑",
    },
    showSecond:{
      type:Boolean,
      default:true,
    },
    secondLabel: {
      // 第三个按钮的标题
      type: String,
      default: "删除",
    },
    showThird: {
      // 是否显示第三个按钮
      type: Boolean,
      default: false,
    },
    thirdLabel: {
      // 第三个按钮的标题
      type: String,
      default: "橘色",
    },
    width: {
      //显示宽度，两个按钮和三个按钮宽度不一样
      type: Number,
      default: 185,
    },
    size: {
      // 尺寸样式
      type: String,
      default: "mini",
    },
    align: {
      // 文本对齐方式
      type: String,
      default: "left",
    },
    maxHeight: {
      // 表格最大高度
      type: Number,
      default: 440,
    },
    showOperation: {
      // 是否显示操作组件
      type: Boolean,
      default: true,
    },
    border: {
      // 是否显示边框
      type: Boolean,
      default: false,
    },
    stripe: {
      // 是否显示斑马线
      type: Boolean,
      default: true,
    },
    highlightCurrentRow: {
      // // 是否高亮当前行
      type: Boolean,
      default: true,
    },
    showOverflowTooltip: {
      // 是否单行显示
      type: Boolean,
      default: true,
    },

    loading: {
      //加载标识
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      // 分页信息
      pageRequest: {
        pageNum: 1,
        pageSize: 9,
      },
      selections: [], // 列表选中列
    };
  },
  methods: {
    // 分页查询
    findPage: function (pageNum) {
      this.$emit("findPage", { pageNum: pageNum });
    },
    // 选择切换
    selectionChange: function (selections) {
      this.selections = selections;
      this.$emit("selectionChange", { selections: selections });
    },
    // 选择切换
    handleCurrentChange: function (val) {
      this.$emit("handleCurrentChange", { val: val });
    },
    // 换页刷新
    refreshPageRequest: function (pageNum) {
      this.findPage(pageNum);
    },
    // 编辑
    handleEdit: function (index, row) {
      this.$emit("handleEdit", { index: index, row: row });
    },
    // 删除
    handleDelete: function (index, row) {
      this.$emit("handleDelete", { index: index, row: row });
    },
    //第三个按钮的功能
    handleThird: function (index, row) {
      this.$emit("handleThird", { index: index, row: row });
    },
  },
  mounted() {
    //this.refreshPageRequest(1);
  },
};
</script>

<style scoped>
.el-table {
  margin: 0 auto;
}
</style>