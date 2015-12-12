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
    
    light = abs(light - (light(1) + light(length(light)))/2);
    light = abs(light - (light(1) + light(length(light)))/2);
    light_sig = rthreshold(light, 600);
    light_sig = abs(1 - light_sig);
    [light_sig1, win] = slidingWin(light_sig, 30, 15);

    len = length(light_sig1);
    peak = 0;
    flag = false;
    for j = 1 : len
        if light_sig1(j) == 0
            if flag == true
                flag = false;
                peak = peak + 1;
            end
        elseif light_sig1(j) == 1
            if flag == false
                flag = true;
            end
        end
    end
    rtn(i) = floor((peak+1)./2);
end

rtn