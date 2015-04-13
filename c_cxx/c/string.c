#include <stdio.h>
#include <string.h>

int main(void)
{
    char str1[12] = "Hello";
    char str2[12] = "World";
    char str3[12];
    int len;

    // copy str1 to str3
    strcpy(str3, str1);
    printf("strcpy( str3, str1): %s\n", str3);

    // cat str1 and str2
    strcat(str1, str2);
    printf("strcat( str1, str2): %s\n", str1);

    // length of str1, after cat
    len = strlen(str1);
    printf("strlen(str1): %d\n", len);

    return 0;
}