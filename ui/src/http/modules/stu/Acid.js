import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Acid/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Acid/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Acid/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Acid/dels/'+data,
        method: 'post',
        // data,
    })
}
