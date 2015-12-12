%threshold algorithm
function event_sig = threshold(sen_data, thresholdValue, inverted)
    data_len = numel(sen_data);
    event_sig = zeros(1, data_len);
    for idx = 1 : data_len
        if sen_data(idx) < thresholdValue
            if inverted == 1 %TRUE 
                event_sig(idx) = 1;
            end
        else
            if inverted == 0 %FALSE
                event_sig(idx) = 1;
            end 
        end
    end
end