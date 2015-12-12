%sen_date: binary array
function event_sig = satCounter(sen_data, counterNum)
    boundary = counterNum / 2;
    state = boundary;
    data_len = numel(sen_data);
    event_sig = zeros(1, data_len);
    for idx = 1 : data_len
        if (sen_data(idx) == 0) && (state > 0)
            state = state - 1;
        elseif (sen_data(idx) == 1) && (state < counterNum)
            state = state + 1;
        end
        if state <= boundary
            event_sig(idx) = 0;
        else
            event_sig(idx) = 1;
        end
    end
end