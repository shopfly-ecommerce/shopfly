-- 专⻔⽤来标志缓存是否被击穿
local flat = redis.call("get",KEYS[1])

-- 如果没有值说明被击穿了
if (not flat)
then
 redis.call("set",KEYS[1],"some value")
 return -1;
end

local value = redis.call("get",KEYS[2])
if  not value
then
 return -1;
else
  value = value + 1
  redis.call("set",KEYS[2],value)
  return value;
end

