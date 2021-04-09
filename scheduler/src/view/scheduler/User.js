import React, { useState, useCallback, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { AccountBox, Add, Remove } from '@material-ui/icons';
import axios from 'axios';
import { Tab } from '@material-ui/core';

const User = (props) => {
    const [updated, setUpdated] = useState(0);
    const [users, setUsers] = useState([]);
    const [user, setUser] = useState({username:""});
    const [selectedUsers, setSelectedUsers] = useState([]);
    const [updated, setUpdated] = useState(0);

    useEffect(()=>{
        updateUser();
        updateUsers();
    },[]);

    useEffect(()=>{
        setUpdated(props.updated);
    },[props.updated])

    const updateUser = () => {
        const url = "/api/user/account/one";
        axios.get(url).then(res=>{
            if (res.data.result === 1) {
                setUser(res.data.user);
            }
        })
    }

    const updateUsers = () => {
        const url = "/api/user/account/all";
        axios.get(url).then(res=>{
            setUsers(res.data.users);
        })
    }

    const deselectUser = user => {
        for (let i = 0; i < selectedUsers.length; i++) {
            if (selectedUsers[i] === user) {
                selectedUsers.splice(i,0);
                users.push(user);
                update();
            }
        }
    }

    const selectUser = user => {
        users.push(user);
        update();
    }

    const update = () => {
        props.update();
    }

    const userTableCells = users.map((user,index)=>
        <TableRow>
            <TableCell key={user.id}>
                <Add className="user-add-button" 
                    size="small"
                    onClick={e=>{selectUser(user)}}/> {user.username}
            </TableCell>
        </TableRow>
    )

    const selectedUserTableCelss = selectedUsers.map((user,index)=>
        <TableRow>
            <TableCell key={user.id}>
                <Remove className="user-add-button" 
                    size="small"
                    onClick={e=>{deselectUser(user)}}/> {user.username}
            </TableCell>
        </TableRow>
    )

    return (
        <Paper className="user-table-container">
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
                                <AccountBox/>{user.username} 
                            </TableCell>
                        </TableRow>
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
        </Paper>
    )
}

export default User