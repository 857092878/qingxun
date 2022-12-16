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
        "id": 1,
        "code": "01",
        "name": "北京科技大学",
        "memo": "测试北京科技大学",
        "children": [
          {
            "id": 2,
            "code": "0101",
            "name": "机械工程学院",
            "memo": "测试机械工程学院",
            "children": [
              {
                "id": 3,
                "code": "010101",
                "name": "物流教研室",
                "memo": "物流教研室"
              },
              {
                "id": 4,
                "code": "010102",
                "name": "工业工程教研室",
                "memo": "测试工业工程教研室"
              }
            ]
          }
        ]
      },
      {
        "id": 5,
        "code": "02",
        "name": "清华大学",
        "memo": "清华大学",
        "children": [
          {
            "id": 6,
            "code": "0201",
            "name": "精密仪器系",
            "memo": "精仪系"
          },
          {
            "id": 7,           
            "code": "0202",
            "name": "生物系",
            "memo": "生物系提农"
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
