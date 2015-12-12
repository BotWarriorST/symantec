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
    
    lpFilt = designfilt('lowpassfir','PassbandFrequency',0.2, ...
         'StopbandFrequency',0.3,'PassbandRipple',0.5, ...
         'StopbandAttenuation',40,'DesignMethod','kaiserwin');
    fvtool(lpFilt);
    acc_deff_lp = filter(lpFilt,diff(acc));

    acc = acc_deff_lp;
    acc_sig = rthreshold(acc, 150);
    acc_sig = abs(1 - acc_sig);
    [acc_sig1, win] = slidingWin(acc_sig, 10, 2);

    len = length(acc_sig1);
    peak = 0;
    flag = false;
    for j = 1 : len
        if acc_sig1(j) == 0
            if flag == true
                flag = false;
                peak = peak + 1;
            end
        elseif acc_sig1(j) == 1
            if flag == false
                flag = true;
            end
        end
    end
    rtn(i) = floor((peak+1)./2);
end

rtn