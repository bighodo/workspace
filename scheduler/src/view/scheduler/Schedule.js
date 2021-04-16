import React, { useState, useCallback, useEffect, useRef } from 'react';
import Paper from '@material-ui/core/Paper';
import {
    Scheduler,
    Appointments,

    WeekView,
    DragDropProvider,
    AppointmentForm,
    AppointmentTooltip,

    Resources,
    GroupingPanel,
} from '@devexpress/dx-react-scheduler-material-ui';
import { 
    ViewState, 
    GroupingState, 
    IntegratedGrouping, 
    IntegratedEditing, 
    EditingState, 
} from '@devexpress/dx-react-scheduler';
import axios from 'axios';
import { red } from '@material-ui/core/colors';
import { Alert } from 'bootstrap';

const viewStyle = [
    { text: "Individual", id: 0,},
    { text: "Total", id: 1}
];

const resources = [{
    fieldName: 'viewStyle',
    title: 'ViewStyle',
    instances: viewStyle
}];

const grouping = [{
    resourceName: 'viewStyle'
}];


const testAppoints = [{
    startDate: new Date(1618475269351),
    endDate: new Date(1618485269351),
    title: "test"
}];

//import appointments from '../../../demo-data/today-appointments';
const Schedule = (props) => {
    const [height, setHeight] = useState(window.innerHeight);
    const [updated, setUpdated] = useState(0);

    const [appointments, setAppointments] = useState([]);
    const [totalAppointments, setTotalAppointments] = useState([]);
    const [appIndexTable, setAppIndexTable] = useState({});

    const schedulerTable = useRef();

    useEffect(()=>{
        if (window.innerWidth / 10 < 150) {
            schedulerTable.current.style.left = '150px';
        } else {
            schedulerTable.current.style.left = '10%';
        }
    },[schedulerTable])

    const resizeEvent = useCallback(()=>{
        if (schedulerTable.current !== undefined) {
            if (window.innerWidth / 10 < 150) {
                schedulerTable.current.style.left = '150px';
            } else {
                schedulerTable.current.style.left = '10%';
            }
        }
        setHeight(window.innerHeight);
    },[schedulerTable])

    useEffect(()=>{
        window.addEventListener("resize",resizeEvent)
        return ()=>{
            window.removeEventListener("resize",resizeEvent);
        };
    },[resizeEvent]);

    useEffect(()=>{
        let appoints = [];
        for (let i = 0; i < props.user.appointments.length; i++) {
            let appointment = props.user.appointments[i];
            appoints.push(appointment);
        }
        for (let i = 0; i < props.selectedUsers.length; i++) {
            let selected = props.selectedUsers[i];
            for (let j = 0; j < selected.appointments.length; j++) {
                appoints.push(selected.appointments[j]);
            }
        }
        setAppointments(appoints);
    },[props.user, props.selectedUsers])

    useEffect(()=>{
        let indexTable = {};
        for (let i = 0; i < appointments.length; i++) {
            indexTable[appointments[i].id] = i;
        }
        setAppIndexTable(indexTable);
    },[appointments])

    const update = () => {
        setUpdated(updated+1);
    }

    const createAppointment = (appoint) => {
        if (appoint === undefined) return;
        let startDate = {
            year: appoint.startDate.getFullYear(),
            month: appoint.startDate.getMonth(),
            date: appoint.startDate.getDate(),
            hours: appoint.startDate.getHours(),
            minutes: appoint.startDate.getMinutes()
        };
        let endDate = {
            year: appoint.endDate.getFullYear(),
            month: appoint.endDate.getMonth(),
            date: appoint.endDate.getDate(),
            hours: appoint.endDate.getHours(),
            minutes: appoint.endDate.getMinutes()
        }
        let data = {
            startDate: startDate,
            endDate: endDate,
        };
        const url = "/api/user/appointment/one"
        axios.post(url, data).then(res=>{
            if (res.data.result === 1) {
                window.Alert('success',"Create appointment.");
                let appoint = res.data.appointment;
                appoint.viewStyle = 0;
                props.user.appointments.push(appoint);
                setAppointments(appointments.concat(appoint));
            } else {
                window.Alert('error', 'Fail to create appointment.');
            }
        })
    }

    useEffect(()=>{
        props.setCreateAppointment(createAppointment);
    },[createAppointment]);

    const updateAppointment = (appoints) => {
        for (let i = 0; i < appoints.length; i++){
            let appoint = appoints[i],
                start = new Date(appoint.startDate),
                end = new Date(appoint.endDate);

            let startDate = {
                year: start.getFullYear(),
                month: start.getMonth(),
                date: start.getDate(),
                hours: start.getHours(),
                minutes: start.getMinutes()
            };
            let endDate = {
                year: end.getFullYear(),
                month: end.getMonth(),
                date: end.getDate(),
                hours: end.getHours(),
                minutes: end.getMinutes()
            }
            let data = {
                id: appoint.id,
                startDate: startDate,
                endDate: endDate,
            };
            const url = "/api/user/appointment/one"
            axios.patch(url, data).then(res=>{
                console.log(res);
                if (res.data.result === 1) {
                    props.updateUser();
                }
            })
        }
    }

    const onCommitChanges = useCallback(target => {
        console.log(target);
        if (target.added) {
            createAppointment(target.added);
        }
        if (target.changed) {
            let changed = [];
            for (let id in target.changed) {
                let selected = target.changed[id];

                let index = appointments.findIndex(e=>e.id === id);
                if (index < 0) continue;
                
                let appoint = appointments[index];
                if (appoint.user.id === props.user.id) {
                    appoint.startDate = selected.startDate.valueOf();
                    appoint.endDate = selected.endDate.valueOf();
                    changed.push(appoint);
                }
            }
            setAppointments(appointments.concat());
            updateAppointment(changed)
        }
        if(target.deleted) {
        }
    }, [appointments,update]);

    const isMine = appoint => {
        if (appoint.user === undefined) return false;
        if (appoint.user.id !== props.user.id) return false;
        return true;
    }

    const timeTableCell = ({ onDoubleClick, ...restProps }) => {
        if (restProps.groupingInfo[0].id === 0){
            let targetTime = {startDate: restProps.startDate, endDate: restProps.endDate}
            // return <WeekView.TimeTableCell onDoubleClick={()=>{createAppointment(targetTime)}}/>;
            return <WeekView.TimeTableCell onDoubleClick={(e)=>{
                createAppointment(targetTime)
            }}/>;
        }
        else
            return <WeekView.TimeTableCell/>
    };


    return (
        <Paper className="scheduler-container" ref={schedulerTable}>
            <Scheduler data={appointments} height={height}>
                <ViewState/>
                <EditingState onCommitChanges={onCommitChanges} />
                <IntegratedEditing />
                <WeekView
                    startDayHour={0}
                    endDayHour={24}
                />
                <Appointments />
                <AppointmentTooltip showDeleteButton/>
                <GroupingState grouping={grouping}/>
                <Resources
                    data={resources}
                    mainResourceName="viewStyle" />
                <IntegratedGrouping />
                <GroupingPanel />
                <DragDropProvider
                    allowDrag={isMine}
                    allowResize={isMine}/>
            </Scheduler>
        </Paper>
    );
}

export default Schedule


const numFormat = variable => {
    variable = Number(variable).toString();
    if (Number(variable) < 10 && variable.length == 1)
        variable = "0" + variable;
    return variable;
}
