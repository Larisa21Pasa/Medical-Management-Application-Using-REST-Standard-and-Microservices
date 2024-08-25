package com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: authenticationclient.proto")
public final class AuthenticationServiceGrpc {

  private AuthenticationServiceGrpc() {}

  public static final String SERVICE_NAME = "AuthenticationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> getAuthenticateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "authenticate",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> getAuthenticateMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> getAuthenticateMethod;
    if ((getAuthenticateMethod = AuthenticationServiceGrpc.getAuthenticateMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getAuthenticateMethod = AuthenticationServiceGrpc.getAuthenticateMethod) == null) {
          AuthenticationServiceGrpc.getAuthenticateMethod = getAuthenticateMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "authenticate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("authenticate"))
                  .build();
          }
        }
     }
     return getAuthenticateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "register",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> getRegisterMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> getRegisterMethod;
    if ((getRegisterMethod = AuthenticationServiceGrpc.getRegisterMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getRegisterMethod = AuthenticationServiceGrpc.getRegisterMethod) == null) {
          AuthenticationServiceGrpc.getRegisterMethod = getRegisterMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("register"))
                  .build();
          }
        }
     }
     return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "logout",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> getLogoutMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> getLogoutMethod;
    if ((getLogoutMethod = AuthenticationServiceGrpc.getLogoutMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getLogoutMethod = AuthenticationServiceGrpc.getLogoutMethod) == null) {
          AuthenticationServiceGrpc.getLogoutMethod = getLogoutMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("logout"))
                  .build();
          }
        }
     }
     return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> getIsTokenValidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "isTokenValid",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> getIsTokenValidMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> getIsTokenValidMethod;
    if ((getIsTokenValidMethod = AuthenticationServiceGrpc.getIsTokenValidMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getIsTokenValidMethod = AuthenticationServiceGrpc.getIsTokenValidMethod) == null) {
          AuthenticationServiceGrpc.getIsTokenValidMethod = getIsTokenValidMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "isTokenValid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("isTokenValid"))
                  .build();
          }
        }
     }
     return getIsTokenValidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserById",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByIdMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByIdMethod;
    if ((getGetUserByIdMethod = AuthenticationServiceGrpc.getGetUserByIdMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getGetUserByIdMethod = AuthenticationServiceGrpc.getGetUserByIdMethod) == null) {
          AuthenticationServiceGrpc.getGetUserByIdMethod = getGetUserByIdMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "getUserById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("getUserById"))
                  .build();
          }
        }
     }
     return getGetUserByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserByEmail",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByEmailMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getGetUserByEmailMethod;
    if ((getGetUserByEmailMethod = AuthenticationServiceGrpc.getGetUserByEmailMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getGetUserByEmailMethod = AuthenticationServiceGrpc.getGetUserByEmailMethod) == null) {
          AuthenticationServiceGrpc.getGetUserByEmailMethod = getGetUserByEmailMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "getUserByEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("getUserByEmail"))
                  .build();
          }
        }
     }
     return getGetUserByEmailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteUser",
      requestType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto.class,
      responseType = com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto,
      com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> getDeleteUserMethod;
    if ((getDeleteUserMethod = AuthenticationServiceGrpc.getDeleteUserMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getDeleteUserMethod = AuthenticationServiceGrpc.getDeleteUserMethod) == null) {
          AuthenticationServiceGrpc.getDeleteUserMethod = getDeleteUserMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto, com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "deleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("deleteUser"))
                  .build();
          }
        }
     }
     return getDeleteUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthenticationServiceStub newStub(io.grpc.Channel channel) {
    return new AuthenticationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthenticationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AuthenticationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthenticationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AuthenticationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AuthenticationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticate(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthenticateMethod(), responseObserver);
    }

    /**
     */
    public void register(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     */
    public void logout(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void isTokenValid(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getIsTokenValidMethod(), responseObserver);
    }

    /**
     */
    public void getUserById(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByIdMethod(), responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByEmailMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto>(
                  this, METHODID_AUTHENTICATE)))
          .addMethod(
            getRegisterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getLogoutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getIsTokenValidMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto>(
                  this, METHODID_IS_TOKEN_VALID)))
          .addMethod(
            getGetUserByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>(
                  this, METHODID_GET_USER_BY_ID)))
          .addMethod(
            getGetUserByEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>(
                  this, METHODID_GET_USER_BY_EMAIL)))
          .addMethod(
            getDeleteUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto,
                com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto>(
                  this, METHODID_DELETE_USER)))
          .build();
    }
  }

  /**
   */
  public static final class AuthenticationServiceStub extends io.grpc.stub.AbstractStub<AuthenticationServiceStub> {
    private AuthenticationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticate(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void register(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void isTokenValid(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIsTokenValidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserById(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthenticationServiceBlockingStub extends io.grpc.stub.AbstractStub<AuthenticationServiceBlockingStub> {
    private AuthenticationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto authenticate(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getAuthenticateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto register(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto logout(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto isTokenValid(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getIsTokenValidMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto getUserById(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto getUserByEmail(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByEmailMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto deleteUser(com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthenticationServiceFutureStub extends io.grpc.stub.AbstractStub<AuthenticationServiceFutureStub> {
    private AuthenticationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto> authenticate(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto> register(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto> logout(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto> isTokenValid(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getIsTokenValidMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getUserById(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto> getUserByEmail(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto> deleteUser(
        com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE = 0;
  private static final int METHODID_REGISTER = 1;
  private static final int METHODID_LOGOUT = 2;
  private static final int METHODID_IS_TOKEN_VALID = 3;
  private static final int METHODID_GET_USER_BY_ID = 4;
  private static final int METHODID_GET_USER_BY_EMAIL = 5;
  private static final int METHODID_DELETE_USER = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthenticationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthenticationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE:
          serviceImpl.authenticate((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AuthenticationResponseProto>) responseObserver);
          break;
        case METHODID_REGISTER:
          serviceImpl.register((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.RegisterResponseProto>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.LogoutResponseProto>) responseObserver);
          break;
        case METHODID_IS_TOKEN_VALID:
          serviceImpl.isTokenValid((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.AccessTokenRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.TokenValidityResponseProto>) responseObserver);
          break;
        case METHODID_GET_USER_BY_ID:
          serviceImpl.getUserById((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByIdRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>) responseObserver);
          break;
        case METHODID_GET_USER_BY_EMAIL:
          serviceImpl.getUserByEmail((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.GetUserByEmailRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.UserResponseProto>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.DeleteUserResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthenticationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.authenticationclient.AuthenticationComponentClient.protoComponentsClient.Authenticationclient.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthenticationService");
    }
  }

  private static final class AuthenticationServiceFileDescriptorSupplier
      extends AuthenticationServiceBaseDescriptorSupplier {
    AuthenticationServiceFileDescriptorSupplier() {}
  }

  private static final class AuthenticationServiceMethodDescriptorSupplier
      extends AuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthenticationServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthenticationServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateMethod())
              .addMethod(getRegisterMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getIsTokenValidMethod())
              .addMethod(getGetUserByIdMethod())
              .addMethod(getGetUserByEmailMethod())
              .addMethod(getDeleteUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
