function eventNum = countEventNum(event_sig)
    cur_stat = 0;
    prv_stat = 0;
    eventNum = 0;
    data_len = numel(event_sig);
    for idx = 1 : data_len
        cur_stat = event_sig(idx);
        if (cur_stat == 1) && (prv_stat == 0)
            cur_stat = 1;
            eventNum = eventNum + 1;
        end
        prv_stat = cur_stat;
    end
end