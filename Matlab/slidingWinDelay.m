%sliding window algorithm
function [event_sig, win] = slidingWinDelay(sen_data, win_size, win_thresh, delay)
    cur_stat = 0;
    prv_stat = 0;
    data_len = numel(sen_data);
    event_sig = zeros(1, data_len);
    win = zeros(1, data_len);
    win_sum = 0;
    for idx = delay : data_len
        win_sum = win_sum + sen_data(idx);
        if idx > (win_size + delay)
            win_sum = win_sum - sen_data(idx - win_size);  
        end
        win(idx) = win_sum;
        if win_sum > win_thresh
            cur_stat = 1;
        else
            cur_stat = 0;
        end;
        if (cur_stat == 1) && (prv_stat == 0)
            cur_stat = 1;
        end
        prv_stat = cur_stat;
        event_sig(idx) = cur_stat;
    end
end