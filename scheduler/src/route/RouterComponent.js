import React from 'react';
import { BrowserRouter, Route, Switch} from 'react-router-dom';
import Scheduler from "../view/scheduler/Main";
import Test from "../view/Test";
import Login from "../view/Login";
import SignUp from "../view/SignUp";

const AppRouter = () => {
   return(
     <div>
       <BrowserRouter>
        {/* <div style={style}> */}
        <div>
          <Switch>
            <Route exact path="/" component={Scheduler} />
            <Route exact path="/test" component={Test} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/signup" component={SignUp} />
          </Switch>
        </div>
       </BrowserRouter>
     </div>
   );
}

// const style = {
//   color: 'red',
//   margin: '10px'
// }

export default AppRouter;