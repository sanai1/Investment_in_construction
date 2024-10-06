#include <jni.h>
#include <string>
#include <iostream>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_investmentinconstruction_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_investmentinconstruction_InteractionJSON_contractWithCJNI(JNIEnv *env, jobject thiz, jstring json) {


    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}