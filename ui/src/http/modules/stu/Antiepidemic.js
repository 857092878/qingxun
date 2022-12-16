import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Antiepidemic/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Antiepidemic/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Antiepidemic/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Antiepidemic/dels/'+data,
        method: 'post',
        // data,
    })
}
