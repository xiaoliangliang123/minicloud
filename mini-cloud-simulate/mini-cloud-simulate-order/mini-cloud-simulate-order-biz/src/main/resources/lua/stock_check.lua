for i = 1,#KEYS
do
local qty=redis.call('get',KEYS[i])
local stock=tonumber(qty)
local quantity=tonumber(ARGV[i])
if (quantity >= stock )
then
return 0
end;
end;
for i = 1,#KEYS
do
local qty=redis.call('get',KEYS[i])
local stock=tonumber(qty)
local quantity=tonumber(ARGV[i])
redis.call('incrby', KEYS[i], -quantity)
end;
return 1

