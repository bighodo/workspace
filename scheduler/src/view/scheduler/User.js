import React, { useState, useCallback, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import { AccountBox, Add, Remove } from '@material-ui/icons';
import { Tab } from '@material-ui/core';

const User = (props) => {
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
                        <TableRow style={{backgroundColor:"#B0B0B0"}}>
                            <TableCell/>
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
                        <TableRow style={{backgroundColor:"#B0B0B0"}}>
                            <TableCell/>
                        </TableRow>
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