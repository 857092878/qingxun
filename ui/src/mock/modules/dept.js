/* 
 * 机构管理模块
 */

// 保存
export function save() {
  return {
    url: 'dept/save',
    type: 'post',
    data: {
      "code": 200,
      "msg": null,
      "data": 1
    }
  }
}
// 批量删除
export function batchDelete() {
  return {
    url: 'dept/delete',
    type: 'post',
    data: {
      "code": 200,
      "msg": null,
      "data": 1
    }
  }
}
export function findDeptTree(params) {
  // 查询机构树
  let findTreeData = {
    "code": 200,
    "msg": null,
    "data": [
      {
        "id": 12,
        "memo": "string",
        "code": "01",
        "name": "北京科技大学",
        "deleted": 0,
        "children": [
          {
            "id": 13,
            "memo": "string",
            "code": "0102",
            "name": "北京科技大学11",
            "deleted": 0,
            "children": [
              {
                "id": 125,
                "memo": "111",
                "code": "010201",
                "name": "部门名称",
                "deleted": 0,
                "children": []
              }
            ]
          },
          {
            "id": 126,
            "memo": "这是劳动处，弄错了我",
            "code": "0105",
            "name": "劳动处",
            "deleted": 0,
            "children": []
          },
          {
            "id": 127,
            "memo": "测试44",
            "code": "0106",
            "name": "物流系机械",
            "deleted": 0,
            "children": []
          },
          {
            "id": 129,
            "memo": "string",
            "code": "0107",
            "name": "我的中国心",
            "deleted": 0,
            "children": []
          },
          {
            "id": 136,
            "memo": "string",
            "code": "0109",
            "name": "name1",
            "deleted": 0,
            "children": []
          },
          {
            "id": 147,
            "memo": "string",
            "code": "0111",
            "name": "name122",
            "deleted": 0,
            "children": []
          }
        ]
      }
    ]
  }
  return {
    url: 'dept/findTree',
    type: 'get',
    data: findTreeData
  }
}
