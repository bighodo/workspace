import original from 'axios';

let newAxios = () => {
    let body = (option) => {
        if (option !== undefined)
        return new Promise((resolve, reject) => {
            original(option).then(res=>{resolve(res)})
                .catch(err=>{errorHandler(err)});
        });
    };
    body.get = (url) => {
        return new Promise((resolve, reject) => {
            original.get(url).then(res=>{resolve(res);})
                 .catch(err =>{errorHandler(err)})
        })
    };
    body.post = (url,data) => {
        return new Promise((resolve, reject) => {
            original.post(url,data).then(res=>{resolve(res);})
                 .catch(err =>{errorHandler(err)})
        })
    };
    body.patch = (url,data) => {
        return new Promise((resolve, reject) => {
            original.patch(url,data).then(res=>{resolve(res);})
                 .catch(err =>{errorHandler(err)})
        })
    };
    body.delete = (url) => {
        return new Promise((resolve, reject) => {
            original.delete(url).then(res=>{resolve(res);})
                 .catch(err =>{errorHandler(err)})
        })
    }
    return body;
}

const axios = newAxios();
export default axios;

const errorHandler = err => {
    let error = err.response;
    if (error === undefined) {
        console.log("Rest Error" , err);
        alert(err);
    }
    console.log("Rest Error", error);
    switch(error.status) {
        case 403: error403();
                  break;
        case 404: error404();
                  break;
        case 504: error504();
                  break;
    }
}

const error403 = () => {
    window.location.href = window.location.origin + "/login";
}

const error404 = () => {
    window.location.href = window.location.origin + "/404";
}

const error504 = () => {
    window.location.href = window.location.origin + "/504";
}
