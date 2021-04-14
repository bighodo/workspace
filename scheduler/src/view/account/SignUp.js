import React, { useState, useEffect } from "react";
import axios from '../../utils/Rest';
import { Link } from 'react-router-dom';

const Signup = (props) => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [userId, setUserId] = useState();

    useEffect(() => {
        console.log(props);
    }, [])

    const register = () => {
        const url = '/api/account/register';
        let data = {
            userId: userId,
            username: username,
            password: password
        }
        axios.post(url, data).then(res => {
            if (res.data.result) {
                window.Alert('success', res.data.message);
                props.history.push('/login');
            } else {
                window.Alert('error', res.data.message);
            }
        })
    }

    return (
        // <form>
        <div className="App">
            <div className="outer">
                <div className="inner">
                    <div>
                        <h3>Register</h3>

                        <div className="form-group">
                            <label>User name</label>
                            <input type="username" className="form-control" placeholder="User name"
                                onChange={e => { setUsername(e.target.value) }} />
                        </div>

                        <div className="form-group">
                            <label>ID</label>
                            <input type="userId" className="form-control" placeholder="Enter ID"
                                onChange={e => { setUserId(e.target.value) }} />
                        </div>

                        <div className="form-group">
                            <label>Password</label>
                            <input type="password" className="form-control" placeholder="Enter password"
                                onChange={e => { setPassword(e.target.value) }} />
                        </div>

                        <button className="btn btn-dark btn-lg btn-block"
                            onClick={register}>Register</button>
                        <p className="forgot-password text-right">
                            Already registered <Link to="/login">log in?</Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        // </form>
    );
}
export default Signup