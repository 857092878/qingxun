import axios from '../../axios'

export const save = (data) => {
if (data.id == 0) {
return axios({
url: '/v1/tst/TstTreeshow/insert',
method: 'post',
data
})
} else {
return axios({
url: '/v1/tst/TstTreeshow/update',
method: 'post',
data
})
}
}

export const deleteById = (id) => {
return axios({
url: '/v1/tst/TstTreeshow/delete/'+id,
method: 'get'
})
}

export const findTree = () => {
return axios({
url: 'v1/tst/TstTreeshow/findTree',
method: 'get'
})
}

export const findChoice = () => {
return axios({
url: 'v1/tst/TstTreeshow/findChoice',
method: 'get'
})
}

