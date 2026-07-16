CREATE OR REPLACE TABLE Salaries (
    SalaryID VARCHAR NOT NULL,
    EmployeeID INTEGER,
    BaseSalary NUMBER(12,2),
    Bonus NUMBER(12,2),
    EffectiveDate DATE
);