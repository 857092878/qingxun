import axios from '../../axios'

export const save = (data) => {
    if (data.id == 0) {
        return axios({
            url: '/v1/tst/TstAllshow/insert',
            method: 'post',
            data
        })
    } else {
        return axios({
            url: '/v1/tst/TstAllshow/update',
            method: 'post',
            data
        })
    }
}

export const deleteById = (id) => {
    return axios({
        url: '/v1/tst/TstAllshow/delete/' + id,
        method: 'get'
    })
}

export const find = (data) => {
    return axios({
        url: 'v1/tst/TstAllshow/find',
        method: 'post',
        data
    })
}

export const findChoice = () => {
    return axios({
        url: 'v1/tst/TstAllshow/findChoice',
        method: 'get'
    })
}

