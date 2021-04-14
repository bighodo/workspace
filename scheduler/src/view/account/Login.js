import React, { useState } from 'react';
import axios from '../../utils/Rest';
import { Link } from 'react-router-dom';

const Login = (props) => {
    const [userId, setUserId] = useState();
    const [password, setPassword] = useState();

    const signin = () => {
        const url = '/api/account/login';
        let data = {
            userId: userId,
            password: password
        }
        axios.post(url, data).then(res => {
            if (res.data.result) {
                window.Alert('success', res.data.message);
                props.history.push('/');
            } else {
                window.Alert('error', res.data.message);
            }
        })
    }

    return (
        <div className="App">
            <div className="outer">
                <div className="inner">
                    <div>
                        <h3>Log in</h3>

                        <div className="form-group">
                            <label>ID</label>
                            <input type="id" className="form-control" placeholder="Enter ID"
                                onChange={e => { setUserId(e.target.value) }} />
                        </div>

                        <div className="form-group">
                            <label>Password</label>
                            <input type="password" className="form-control" placeholder="Enter password"
                                onChange={e => { setPassword(e.target.value) }} />
                        </div>

                        <div className="form-group">
                            <div className="custom-control custom-checkbox">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" />
                                <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                            </div>
                        </div>

                        <button type="submit" className="btn btn-dark btn-lg btn-block"
                            onClick={signin}>Sign in</button>
                        <p className="forgot-password text-right">
                            New to this? <Link to="/signup">Create an account.</Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Login;

// import React, { useState } from "react";
// import axios from "axios";
// const Login = () => {
//     const [id, setId] = useState();
//     const [pw, setPw] = useState();

//     const tryLogin = () => {
//         console.log(id);
//         const url = "/api/account/login";

//         let data = {
//             userId : id,
//             password: pw
//         }
//         axios.post(url, data).then(res=>{
//             if (res.data.result === 1) window.location.href="/";
//             else alert(res.data.message);
//         })
//     }
//     return (
//         <form>
//             <h3>Log in</h3>
//             <div className="form-group">
//                 <label>Id</label>
//                 <input type="email" className="form-control" placeholder="ID"
//                     onChange={(e)=>{setId(e.target.value)}}/>
//             </div>
//             <div className="form-group">
//                 <label>Password</label>
//                 <input type="password" className="form-control" placeholder="Enter password"
//                     onChange={(e)=>{setPw(e.target.value)}}/>
//             </div>
//             <div className="form-group">
//                 <div className="custom-control custom-checkbox">
//                     <input type="checkbox" className="custom-control-input" id="customCheck1" />
//                     <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
//                 </div>
//             </div>
//             <button className="btn btn-dark btn-lg btn-block"
//                 onClick={tryLogin}>Sign in</button>
//             <p className="forgot-password text-right">
//                 Forgot <a href="#">password?</a>
//             </p>
//         </form>
//     );
// }
// export default Login;