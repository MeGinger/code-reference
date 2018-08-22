/**
 *  IP to CIDR
Given a start IP address ip and a number of ips we need to cover n, return a representation of the range as a list (of smallest possible length) of CIDR blocks.

A CIDR block is a string consisting of an IP, followed by a slash, and then the prefix length. For example: "123.45.67.89/20". That prefix length "20" represents the number of common prefix bits in the specified range.

Example 1:
Input: ip = "255.0.0.7", n = 10
Output: ["255.0.0.7/32","255.0.0.8/29","255.0.0.16/32"]
Explanation:
The initial ip address, when converted to binary, looks like this (spaces added for clarity):
255.0.0.7 -> 11111111 00000000 00000000 00000111
The address "255.0.0.7/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just this one address.

The address "255.0.0.8/29" specifies all addresses with a common prefix of 29 bits to the given address:
255.0.0.8 -> 11111111 00000000 00000000 00001000
Addresses with common prefix of 29 bits are:
11111111 00000000 00000000 00001000
11111111 00000000 00000000 00001001
11111111 00000000 00000000 00001010
11111111 00000000 00000000 00001011
11111111 00000000 00000000 00001100
11111111 00000000 00000000 00001101
11111111 00000000 00000000 00001110
11111111 00000000 00000000 00001111

The address "255.0.0.16/32" specifies all addresses with a common prefix of 32 bits to the given address,
ie. just 11111111 00000000 00000000 00010000.

In total, the answer specifies the range of 10 ips starting with the address 255.0.0.7 .

There were other representations, such as:
["255.0.0.7/32","255.0.0.8/30", "255.0.0.12/30", "255.0.0.16/32"],
but our answer was the shortest possible.

Also note that a representation beginning with say, "255.0.0.7/30" would be incorrect,
because it includes addresses like 255.0.0.4 = 11111111 00000000 00000000 00000100 
that are outside the specified range.
Note:
ip will be a valid IPv4 address.
Every implied address ip + x (for x < n) will be a valid IPv4 address.
n will be an integer in the range [1, 1000].

255.0.0.7/32

255.0.0.8/29
255.0.0.8   1000
255.0.0.9   1001 => 2^3 + 2^0 = 9
255.0.0.10   1010

0 0 0
2*2*2 => 8
255.0.0.8 -> 255.0.0.15

00000000 11111111 11111111 11111111
   00       FF      FF       FF
  &
  127       10      138      128
  
 */
    public List<String> ipToCIDR(String ip, int range) {
        long start = ipToLong(ip);
        List<String> result = new ArrayList<>();
        while (range > 0) {
            // identify the location of first 1's from lower bit to higher bit
            // of start IP
            int diff1 = Long.numberOfTrailingZeros(start);

            // calculate how many IP addresses between the start and end
            // e.g. between 1.1.1.112 and 1.1.1.119, there are 8 IP address
            // 3 bits to represent 8 IPs, from 1.1.1.112 to 1.1.1.119
            int diff2 = Long.numberOfTrailingZeros(Long.highestOneBit(range));

            int diff = Math.min(diff1, diff2);

            // Add to results
            String ipString = longToIP(start);
            result.add(ipString + "/" + (32 - diff));
            // We have included 2^diff numbers of IP into result
            int count = 1 << diff; // 0
            start += count;//255.0.0.10
            range -= count;
        }
        return result;
    }

    private long ipToLong(String strIP) {
        String[] ipSegs = strIP.split("\\.");
        long res = 0;
        for (int i = 0; i < 4; i++) {
            res += Long.valueOf(ipSegs[i]) << (8 * (3 - i));
        }
        return res;
    }

    private String longToIP(long longIP) {
        StringBuffer sb = new StringBuffer();
        sb.append(longIP >>> 24).append(".").append((longIP & 0x00FFFFFF) >>> 16).append(".")
                .append((longIP & 0x0000FFFF) >>> 8).append(".")
                .append(longIP & 0x000000FF);
        return sb.toString();
    }