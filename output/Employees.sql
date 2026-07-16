CREATE OR REPLACE TABLE Employees (
    EmployeeID VARCHAR NOT NULL,
    DepartmentID INTEGER NOT NULL,
    FirstName VARCHAR(100),
    LastName VARCHAR(100),
    Email VARCHAR(150),
    Phone VARCHAR(20),
    HireDate DATE,
    JobTitle VARCHAR(100),
    Status VARCHAR(20)
);