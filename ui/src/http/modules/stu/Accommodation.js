import axios from '../../axios'

export const findPage = (data) => {
    return axios({
        url: '/stu/Accommodation/findPage',
        method: 'post',
        data,
    })
}
export const add = (data) => {
    return axios({
        url: '/stu/Accommodation/add',
        method: 'post',
        data,
    })
}
export const del = (data) => {
    return axios({
        url: '/stu/Accommodation/del/'+data,
        method: 'post',
        // data,
    })
}
export const dels = (data) => {
    return axios({
        url: '/stu/Accommodation/dels/'+data,
        method: 'post',
        // data,
    })
}
