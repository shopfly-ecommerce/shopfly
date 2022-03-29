-- 可能回滚的列表，一个记录要回滚的skuid一个记录库存
local skuid_list= {}
local stock_list= {}

local arg_list = ARGV;
local function cut ( key , num )
    local value = redis.call("get",key)
    value=value-num
    if(value<0)
    then
        -- 发生超卖
        return false;
    end
    redis.call("set",key,value)
    return true
end

local function rollback ( )
    for i,k in ipairs (skuid_list) do
        -- 加上原来减掉的库存
        redis.call("incrby",k,stock_list[i])
    end
end

local function doExec()
    for i, k in ipairs (arg_list)
    do
        local num = k
        local key= KEYS[i]
        local result = cut(key,num)

        -- 发生超卖，需要回滚
        if (result == false)
        then
            rollback()
            return false
        else
            -- 记录可能要回滚的数据
            table.insert(skuid_list,key)
            table.insert(stock_list,num)
        end

    end
    return true;
end

return doExec()