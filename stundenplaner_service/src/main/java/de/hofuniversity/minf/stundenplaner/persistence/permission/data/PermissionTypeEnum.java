package de.hofuniversity.minf.stundenplaner.persistence.permission.data;

public enum PermissionTypeEnum {
    //delegatedLecturer permissions
    CAN_READ_DELEGATED_LECTURERS,
    //faculty permissions
    CAN_READ_FACULTIES,
    //lecture permissions
    CAN_CREATE_LECTURES,
    CAN_READ_LECTURES,
    CAN_UPDATE_LECTURES,
    CAN_DELETE_LECTURES,
    //lecturerProfile permissions
    CAN_READ_LECTURER_PROFILES,
    //modulePreference permissions
    CAN_READ_MODULE_PREFERENCES,
    //program permissions
    CAN_CREATE_PROGRAMS,
    CAN_READ_PROGRAMS,
    CAN_UPDATE_PROGRAMS,
    CAN_DELETE_PROGRAMS,
    //role permissions
    CAN_READ_ROLES,
    //room permissions
    CAN_CREATE_ROOMS,
    CAN_READ_ROOMS,
    CAN_UPDATE_ROOMS,
    CAN_DELETE_ROOMS,
    //semester permissions
    CAN_CREATE_SEMESTERS,
    CAN_READ_SEMESTERS,
    CAN_UPDATE_SEMESTERS,
    CAN_DELETE_SEMESTERS,
    //timeslot permissions
    CAN_READ_TIME_SLOTS,
    //timeslotPreference permissions
    CAN_READ_TIME_SLOT_PREFERENCES,
    //timetable permissions
    CAN_CREATE_TIME_TABLES,
    CAN_READ_TIME_TABLES,
    CAN_DELETE_TIME_TABLES,
    //lesson permissions
    CAN_CREATE_LESSONS,
    CAN_READ_LESSONS,
    CAN_UPDATE_LESSONS,
    CAN_DELETE_LESSONS,
    //user permissions
    CAN_CREATE_USERS,
    CAN_READ_USERS,
    CAN_UPDATE_USERS,
    CAN_DELETE_USERS
}
