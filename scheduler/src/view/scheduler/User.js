import React, { useState, useCallback, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { AccountBox, Add, Remove } from '@material-ui/icons';
import axios from '../../utils/Rest';
import { Tab } from '@material-ui/core';

const User = (props) => {

    useEffect(()=>{
        updateUser();
    },[]);

    useEffect(()=>{
        updateUsers();
    },[props.user])

    const updateUser = () => {
        const url = "/api/user/account/one";
        axios.get(url).then(res=>{
            if (res.data.result === 1) {
                let user = res.data.user;
                for (let i = 0; i < user.appointments.length; i++) {
                    user.appointments[i].viewStyle = 0;
                }
                props.setUser(res.data.user);
            }
        })
    }

    const updateUsers = () => {
        if (props.user.id === undefined) return;
        const url = "/api/user/account/all";
        axios.get(url).then(res=>{
            let users = res.data.users;
            for (let i = users.length - 1; i >= 0; i--) {
                let user = users[i];
                if (props.user.id === user.id) {
                    users.splice(i, 1);
                    break;
                } else {
                    let appointments = user.appointments;
                    for (let i = 0; i < appointments.length; i++) {
                        appointments[i].viewStyle = 0;
                    }
                }
            }
            props.setUsers(res.data.users);
        })
    }

    const userTableCells = props.users.map((user,index)=>
        <TableRow>
            <TableCell key={user.id}>
                <Add className="user-add-button" 
                    size="small"
                    onClick={e=>{props.selectUser(user)}}/> {user.username}
            </TableCell>
        </TableRow>
    )

    const selectedUserTableCelss = props.selectedUsers.map((user,index)=>
        <TableRow>
            <TableCell key={user.id}>
                <Remove className="user-add-button" 
                    size="small"
                    onClick={e=>{props.deselectUser(user)}}/> {user.username}
            </TableCell>
        </TableRow>
    )

    return (
        <div className="user-table-container">
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                ● USER
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell> 
                                <AccountBox/>{props.user.username} 
                            </TableCell>
                        </TableRow>
                        {selectedUserTableCelss}
                    </TableBody>
                </Table>
                <Table>
                    <TableHead>
                        <TableCell>
                        </TableCell>
                    </TableHead>
                    <TableBody>
                        {userTableCells}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}

export default User