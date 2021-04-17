import React, { useState, useCallback, useEffect } from 'react';

import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';

import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import Grid from '@material-ui/core/Grid';

import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';

const getToday = () => {
    return new Date();
}

const MINUTES = 60 * 1000;
const HOURS = 60 * MINUTES;
const DAYS = 24 * HOURS;
const FIRST_DAY_OF_WEEK = 4;

const Modal = (props) => {
    const [startDate, setStartDate] = useState(getToday());
    const [endDate, setEndDate] = useState(new Date(getToday().valueOf() + 1 * HOURS));
    const [rangeOfThisWeek, setRangeOfThisWeek] = useState(isOnThisWeek(new Date()));

    const handleStartDateChange = (date) => {
        if (date < rangeOfThisWeek[0] || date > rangeOfThisWeek[1]) {
            window.Alert("warning","Make appointment on this week!");
            return;
        }
        if (date > endDate) {
            setEndDate(new Date(date.valueOf() + 30 * MINUTES));
        }
        setStartDate(date);
    }

    const handleEndDateChange = (date) => {
        if (date < rangeOfThisWeek[0] || date > rangeOfThisWeek[1]) {
            window.Alert("warning","Make appointment on this week!");
            return;
        }
        if (date < startDate) {
            setStartDate(new Date(date.valueOf() - 30 * MINUTES));
        }
        setEndDate(date);
    }

    const closeModal = () => {
        props.setModalOpen(false);
    };


    const createAppointment = () => {
        let appointment = {
            startDate: startDate,
            endDate: endDate
        };
        props.createAppointment[0](appointment);
        closeModal();
    }

    return (
        <Paper>
            <Dialog
                open={props.modalOpen}
                // onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">{"Make appointment"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Cannot make appointment within the range already exists
                        and out of this week.
                    </DialogContentText>

                    <Grid container justify="space-around">
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid item xs={6} justify="space-around">
                            <Grid container justify="space-around">
                                <KeyboardDatePicker
                                    margin="normal"
                                    id="date-picker-dialog"
                                    label="Start date"
                                    format="MM/dd/yyyy"
                                    value={startDate}
                                    onChange={handleStartDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />
                            </Grid>
                            <Grid container justify="space-around">
                                <KeyboardTimePicker
                                    margin="normal"
                                    id="time-picker"
                                    label="Start time"
                                    value={startDate}
                                    onChange={handleStartDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change time',
                                    }}
                                />
                            </Grid>
                        </Grid>
                        <Grid item xs={6} justify="space-around">
                            <Grid container justify="space-around">
                                <KeyboardDatePicker
                                    margin="normal"
                                    id="date-picker-dialog"
                                    label="End date"
                                    format="MM/dd/yyyy"
                                    value={endDate}
                                    onChange={handleEndDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />
                            </Grid>
                            <Grid container justify="space-around">
                                <KeyboardTimePicker
                                    margin="normal"
                                    id="time-picker"
                                    label="End time"
                                    value={endDate}
                                    onChange={handleEndDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change time',
                                    }}
                                />
                            </Grid>
                        </Grid>
                    </MuiPickersUtilsProvider>
                    </Grid>



                </DialogContent>
                <DialogActions>
                    <Button onClick={closeModal} color="primary">
                        Cancle
                    </Button>
                    <Button onClick={createAppointment} color="primary" autoFocus>
                        Confirm
                    </Button>
                </DialogActions>
            </Dialog>
        </Paper>
    )
}

export default Modal

const isOnThisWeek = date => {
    let day = date.getDay() - FIRST_DAY_OF_WEEK;
    if (day < 0) day += 7;
    let firstDate = new Date(date.valueOf() - (day * DAYS));
    firstDate.setHours(0,0,0,0);
    let lastDate = new Date(firstDate.valueOf()+7*DAYS-1);
    return [firstDate, lastDate];
}