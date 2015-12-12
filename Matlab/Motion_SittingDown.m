rtn = zeros(1, 10);
for i = 1 : 10
    filename = strcat('C:\Users\Simba\Desktop\DATA\Sitting', num2str(i));
    filename = strcat(filename, '.txt');
    delimiter = '\t';
    startRow = 2;
    
    formatSpec = '%f%s%f%f%f%f%f%f%f%f%[^\n\r]';
    fileID = fopen(filename,'r');
    dataArray = textscan(fileID, formatSpec, 'Delimiter', delimiter, 'HeaderLines' ,startRow-1, 'ReturnOnError', false);
    fclose(fileID);
    
    temp = dataArray{:, 1};
    id = dataArray{:, 2};
    light = dataArray{:, 3};
    timestamp = dataArray{:, 4};
    acc_z = dataArray{:, 5};
    acc_y = dataArray{:, 6};
    acc_x = dataArray{:, 7};
    motion = dataArray{:, 8};
    pressure = dataArray{:, 9};
    humid = dataArray{:, 10};
    acc = sqrt((acc_x.*acc_x)+(acc_y.*acc_y)+(acc_z.*acc_z));
    
    [motion_sig, win] = slidingWin(motion, 40, 8);
    
    len = length(motion_sig);
    peak = 0;
    flag = false;
    for j = 1 : len
        if motion_sig(j) == 0
            if flag == true
                flag = false;
                peak = peak + 1;
            end
        elseif motion_sig(j) == 1
            if flag == false
                flag = true;
            end
        end
    end
    rtn(i) = floor((peak+1)./2);
end

rtn