import React, { useState, useCallback, useEffect } from 'react';
import axios from '../../utils/Rest';

import { Add } from '@material-ui/icons';
import Paper from '@material-ui/core/Paper';
import Schedule from './Schedule';
import User from './User';
import Modal from './Modal';


const Main = (props) => {
    const [updated, setUpdated] = useState(0);

    const [users, setUsers] = useState([]);
    const [user, setUser] = useState({username:"", appointments:[]});
    const [selectedUsers, setSelectedUsers] = useState([]);

    useEffect(()=>{
        updateUser();
    },[])

    useEffect(()=>{
        updateUsers();
    },[user])


    const update = () => {
        setUpdated(updated+1);
    }

    const updateUser = () => {
        const url = "/api/user/account/one";
        axios.get(url).then(res=>{
            if (res.data.result === 1) {
                let user = res.data.user;
                for (let i = 0; i < user.appointments.length; i++) {
                    user.appointments[i].viewStyle = 0;
                }
                setUser(res.data.user);
            }
        })
    }

    const updateUsers = () => {
        if (user.id === undefined) return;
        const url = "/api/user/account/all";
        axios.get(url).then(res=>{
            let users = res.data.users;
            for (let i = users.length - 1; i >= 0; i--) {
                let target = users[i];
                if (user.id === target.id) {
                    users.splice(i, 1);
                    break;
                } else {
                    let appointments = target.appointments;
                    for (let i = 0; i < appointments.length; i++) {
                        appointments[i].viewStyle = 0;
                    }
                }
            }
            setUsers(res.data.users);
        })
    }

    const selectUser = target => {
        let index = users.findIndex(e=>e.id === target.id);
        if (index < 0) return;
        users.splice(index, 1);

        setUsers(users.concat());
        setSelectedUsers(selectedUsers.concat(target));
    }

    const deselectUser = target => {
        let index = selectedUsers.findIndex(e=>e.id === target.id);
        if (index < 0) return;
        selectedUsers.splice(index, 1);

        setSelectedUsers(selectedUsers.concat());
        setUsers(users.concat(target));
    }

    return (
        <Paper>
            <div className="add-appointment-background-layer">
                <Add fontSize="midium"/>
            </div>
            <Modal/>
            <User
                user={user}
                users={users}
                selectedUsers={selectedUsers}
                selectUser={selectUser}
                deselectUser={deselectUser}/>
            <Schedule
                user={user}
                selectedUsers={selectedUsers}
                updated={updated}
                update={update}
                updateUser={updateUser}/>
        </Paper>
    )
}

export default Main