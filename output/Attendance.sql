CREATE OR REPLACE TABLE Attendance (
    AttendanceID VARCHAR NOT NULL,
    EmployeeID INTEGER,
    AttendanceDate DATE,
    Status VARCHAR(20),
    WorkingHours NUMBER(4,2)
);