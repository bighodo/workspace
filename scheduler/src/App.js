import React, { useState, useEffect, useCallback } from 'react';
import AppRouter from './route/RouterComponent';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import './App.css';

function App() {

    const [alert, setAlert] = useState({
        open: false,
        type: "success", //error, warning, info, success
        message: "default message."
    })

    const openAlert = useCallback((type, message) => {
        setAlert({
            open: true,
            type: type,
            message: message
        });
    },[setAlert]);

    useEffect(()=>{
        window.Alert = openAlert;
    },[openAlert])

    const rootStyle = {
        width: '100vw',
        height: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
    }

    const alertCloseHandler = (event, reason) => {
        setAlert({
            open: false,
            type: alert.type,
            message: alert.message
        })
    }

    return (
        <div id="react-root" className="react-root" style={rootStyle}>
            <AppRouter />
            <Snackbar
                open={alert.open}
                autoHideDuration={3000}
                onClose={alertCloseHandler}>
                <Alert severity={alert.type}>
                    {alert.message}
                </Alert>
            </Snackbar>
        </div>
    );
}

export default App;
