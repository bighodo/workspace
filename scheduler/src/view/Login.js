import React, { useState } from "react";
import axios from "axios";
const Login = () => {
    const [id, setId] = useState();
    const [pw, setPw] = useState();

    const tryLogin = () => {
        console.log(id);
        const url = "/api/account/login";

        let data = {
            userId : id,
            password: pw
        }
        axios.post(url, data).then(res=>{
            if (res.data.result === 1) window.location.href="/";
            else alert(res.data.message);
        })
    }
    return (
        <form>
            <h3>Log in</h3>
            <div className="form-group">
                <label>Id</label>
                <input type="email" className="form-control" placeholder="ID"
                    onChange={(e)=>{setId(e.target.value)}}/>
            </div>
            <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="Enter password"
                    onChange={(e)=>{setPw(e.target.value)}}/>
            </div>
            <div className="form-group">
                <div className="custom-control custom-checkbox">
                    <input type="checkbox" className="custom-control-input" id="customCheck1" />
                    <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                </div>
            </div>
            <button className="btn btn-dark btn-lg btn-block"
                onClick={tryLogin}>Sign in</button>
            <p className="forgot-password text-right">
                Forgot <a href="#">password?</a>
            </p>
        </form>
    );
}
export default Login;