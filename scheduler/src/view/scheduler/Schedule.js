import React, { useState, useCallback, useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import {
    Scheduler,
    MonthView,
    Toolbar,
    DateNavigator,
    Appointments,
    TodayButton,
    WeekView,
    DragDropProvider,
    AppointmentForm,

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

//import appointments from '../../../demo-data/today-appointments';
const Schedule = (props) => {
    const [height, setHeight] = useState(window.innerHeight);
    const [updated, setUpdated] = useState(0);

    const [appointments, setAppointments] = useState([]);
    const [totalAppointments, setTotalAppointments] = useState([]);
    const [appIndexTable, setAppIndexTable] = useState({});

    const [viewStyle, setViewStyle] = useState([
        { text: "Individual", id: 0,},
        { text: "Total", id: 1}
    ]);
    const [resources, setResources] = useState([{
        fieldName: 'viewStyle',
        title: 'ViewStyle',
        instances: viewStyle
    }]);
    const [grouping, setGrouping] = useState([{
        resourceName: 'viewStyle'
    }]);

    const resizeEvent = useCallback(()=>{
        setHeight(window.innerHeight);
    },[])

    //on init
    useEffect(()=>{
        updateAppointments();
        window.addEventListener("resize",resizeEvent)
        return ()=>{
            window.removeEventListener("resize",resizeEvent);
        };
    },[]);

    useEffect(()=>{
        let appoints = [];
        appoints.push(props.user.appointments);
        for (let i = 0; i < props.selectedUsers.length; i++) {
            let appoint = props.selectedUsers[i];
            appoints.push(appoint);
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

    const today = () => {
        let date = new Date();
        let text = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
        return text;
    }

    const update = () => {
        setUpdated(updated+1);
    }

    const updateAppointments = () => {
        const url = "/api/user/appointment/all";
        axios.get(url).then(res=>{
            if(res.data.result === 1) {
                setAppointments(res.data.appointments);
            }
        })
    }

    const createAppointment = (appoint) => {
        let startDate = {
            year: appoint.startDate.getFullYear(),
            month: appoint.startDate.getMonth()+1,
            date: appoint.startDate.getDate(),
            hours: appoint.startDate.getHours(),
            minutes: appoint.startDate.getMinutes()
        };
        let endDate = {
            year: appoint.endDate.getFullYear(),
            month: appoint.endDate.getMonth()+1,
            date: appoint.endDate.getDate(),
            hours: appoint.endDate.getHours(),
            minutes: appoint.endDate.getMinutes()
        }
        let data = {
            startDate: startDate,
            endDate: endDate,
            title: appoint.title,
            notes: appoint.notes
        };
        const url = "/api/user/appointment/one"
        axios.post(url, data).then(res=>{
            if (res.data.result === 1) {
                let appoint = res.data.appointment;
                let newAppointments = appointments.concat();
                newAppointments.push(appoint);
                setAppointments(newAppointments);
            }
        })
    }

    const updateAppointment = (appoints) => {
        for (let id in appoints){
            let appoint = appoints[id];
            let startDate = {
                year: appoint.startDate.getFullYear(),
                month: appoint.startDate.getMonth()+1,
                date: appoint.startDate.getDate(),
                hours: appoint.startDate.getHours(),
                minutes: appoint.startDate.getMinutes()
            };
            let endDate = {
                year: appoint.endDate.getFullYear(),
                month: appoint.endDate.getMonth()+1,
                date: appoint.endDate.getDate(),
                hours: appoint.endDate.getHours(),
                minutes: appoint.endDate.getMinutes()
            }
            let data = {
                id: id,
                startDate: startDate,
                endDate: endDate,
                title: appoint.title,
                notes: appoint.notes
            };
            const url = "/api/user/appointment/one"
            axios.patch(url, data).then(res=>{
                if (res.data.result === 1) {
                    let appoint = res.data.appointment;
    
                    let newAppointments = appointments.concat();
                    let appointment = newAppointments[appIndexTable[appoint.id]];
                    appointment.startDate = appoint.startDate;
                    appointment.endDate = appoint.endDate;
                    setAppointments(newAppointments);
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
            let newAppointments = appointments.concat();
            for (let id in target.changed) {
                let changed = target.changed[id],
                    appointment = newAppointments[appIndexTable[id]];

                console.log(changed.endDate.getFullYear);
                appointment.startDate = changed.startDate;
                appointment.endDate = changed.endDate;
            }
            setAppointments(newAppointments);
            updateAppointment(target.changed)
        }
        if(target.deleted) {
        }
    }, [appointments,update]);

    const timeTableCell = ({ onDoubleClick, ...restProps }) => {
        return <WeekView.TimeTableCell onDoubleClick={()=>{createAppointment(restProps)}}/>;
    };


    return (
        <Paper className="scheduler-container">
            <Scheduler data={appointments} height={height}>
                <ViewState />
                <EditingState
                    onCommitChanges={onCommitChanges} />
                <GroupingState
                    grouping={grouping}/>
                <WeekView 
                    startDayHour={11} 
                    endDayHour={24} 
                    excludedDays={[1]}
                    timeTableCellComponent={timeTableCell}/>
                {/* <Toolbar />
                <TodayButton />
                <DateNavigator /> */}
                <Appointments />
                <Resources
                    data={resources}
                    mainResourceName="viewStyle"/>
                <IntegratedGrouping />
                <GroupingPanel />

                
                <DragDropProvider allowDrag={() => { return true }} />
                <AppointmentForm />
            </Scheduler>
        </Paper>
    );
}

export default Schedule

const date2Text = date => {
    let text = date.getFullYear()+"-"+
        numFormat(date.getMonth()+1)+"-"+
        numFormat(date.getDate())+"T"+
        numFormat(date.getHours()) + ":"+
        numFormat(date.getMinutes());
    console.log(text);
    return text;
}

const numFormat = variable => {
    variable = Number(variable).toString();
    if (Number(variable) < 10 && variable.length == 1)
        variable = "0" + variable;
    return variable;
}
