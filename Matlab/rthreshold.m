function event_sig = rthreshold( sen_data, thresholdValue )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    data_len = numel(sen_data);
    event_sig = zeros(1, data_len);
    for idx = 1 : data_len
        if abs(sen_data(idx)) < thresholdValue
            event_sig(idx) = 1;
        end
    end

end

