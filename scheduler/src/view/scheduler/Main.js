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

    return (
        <Paper>
            <User
                updated={updated}
                update={update}
                user={user}
                users={users}
                setUser={setUser}
                setUsers={setUsers}/>
            <Schedule
                user={user}
                selectedUsers={selectedUsers}
                updated={updated}
                update={update}/>
        </Paper>
    )
}

export default Main