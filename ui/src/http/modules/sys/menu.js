import axios from '../../axios'

/* 
 * 菜单管理模块
 */

 // 保存
export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/sys/menu/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/sys/menu/update',
            method: 'post',
            data
        })
    }
}
    // 删除
    export const deleteById = (id) => {
        return axios({
            url: '/v1/sys/menu/delete/'+id,
            method: 'get'
        })
    }
// 查找导航菜单树
export const findNavTree = (params) => {
    return axios({
        url: '/menu/findNavTree',
        method: 'get',
        params
    })
}
// 查找导航菜单树
export const findMenuTree = () => {
    return axios({
        url: '/menu/findMenuTree',
        method: 'get'
    })
}

  // 查询机构树
  export const findTree = () => {
    return axios({
        url: 'v1/sys/menu/findTree',
        method: 'get'
    })
}

// 保存
export const saveFunctions = (data) => {
        return axios({
            url: '/v1/sys/menu/saveBatch',
            method: 'post',
            data
        })
}

// 获得存在的数据
export const getExistedFuncs = (data) => {
    return axios({
        url: '/v1/sys/menu/findFunctions',
        method: 'post',
        data
    })
}

