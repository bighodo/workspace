import React from 'react';
import {BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Scheduler from "../view/scheduler/Main";
import Test from "../view/Test";
import Login from "../view/account/Login";
import SignUp from "../view/account/SignUp";

const AppRouter = () => {
    return (
        <Router>
            <Switch>
                <Route exact path='/main' component={Scheduler} />
                <Route path="/login" component={Login} />
                <Route path="/signup" component={SignUp} />
                <Route path="/test" component={Test} />
                <Redirect
                    path="/"
                    to={getCookie("access-token") === null ? "/login" : "/main"} />
            </Switch>
        </Router>
    );
    //  return(
    //    <div>
    //      <BrowserRouter>
    //       {/* <div style={style}> */}
    //       <div>
    //         <Switch>
    //           <Route exact path="/" component={Scheduler} />
    //           <Route exact path="/test" component={Test} />
    //           <Route exact path="/login" component={Login} />
    //           <Route exact path="/signup" component={SignUp} />
    //         </Switch>
    //       </div>
    //      </BrowserRouter>
    //    </div>
    //  );
}

// const style = {
//   color: 'red',
//   margin: '10px'
// }

export default AppRouter;

let getCookie = function (name) {
    let value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
};


let deleteCookie = function (name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}