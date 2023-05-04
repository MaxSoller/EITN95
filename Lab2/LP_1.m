
clc
clear all

c = [-13; -11];
A = [4 5; 5 3; 1 2];
b = [1500; 1575; 420];
lb = [0; 0];
    
options = optimoptions('linprog', 'Algorithm', 'interior-point', 'Display', 'off');
[x, fval] = linprog(c', A, b, [], [], lb, [], options);

disp('Task 1:');
disp('Optimal Solution:');
disp(x);
disp('Optimal value:');
disp(-fval);

dataPoints = [1575, 1590, 1605, 1620, 1635, 1650, 1665, 1680, 1695, 1710, 1725];
values = zeros(11, 1);

A = [-4 -5 -1; -5 -3 -2];
b = [-13; -11];
lb = [0; 0; 0];

for i=1:11    
    raw_mat = dataPoints(i);
    c = [1500; raw_mat; 420];
    
    options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
    [y, fval] = linprog(c', A, b, [], [], lb, [], options);

    values(i) = fval;
    disp('Task 2:');
    disp('Optimal Solution:');
    disp(y);
    disp('Optimal value:');
    disp(fval);
end

disp(values)
plot(dataPoints, values, '-o')
