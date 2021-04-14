import React, { useState, useCallback, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Schedule from './Schedule';
import User from './User';

const Main = (props) => {
    const [updated, setUpdated] = useState(0);

    const [users, setUsers] = useState([]);
    const [user, setUser] = useState({username:"", appointments:[]});
    const [selectedUsers, setSelectedUsers] = useState([]);

    useEffect(()=>{
        setUpdated(props.updated);
    },[props.updated])

    const update = () => {
        setUpdated(updated+1);
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
            <User
                user={user}
                users={users}
                setUser={setUser}
                setUsers={setUsers}
                selectedUsers={selectedUsers}
                selectUser={selectUser}
                deselectUser={deselectUser}/>
            <Schedule
                user={user}
                selectedUsers={selectedUsers}
                updated={updated}
                update={update}/>
        </Paper>
    )
}

export default Main