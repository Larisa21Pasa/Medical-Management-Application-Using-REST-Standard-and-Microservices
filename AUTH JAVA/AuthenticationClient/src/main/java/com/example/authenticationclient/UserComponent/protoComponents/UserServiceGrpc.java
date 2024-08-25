package com.example.authenticationclient.UserComponent.protoComponents;

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
    comments = "Source: user.proto")
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final String SERVICE_NAME = "UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserById",
      requestType = com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto.class,
      responseType = com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByIdMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByIdMethod;
    if ((getGetUserByIdMethod = UserServiceGrpc.getGetUserByIdMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByIdMethod = UserServiceGrpc.getGetUserByIdMethod) == null) {
          UserServiceGrpc.getGetUserByIdMethod = getGetUserByIdMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "UserService", "getUserById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("getUserById"))
                  .build();
          }
        }
     }
     return getGetUserByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserByEmail",
      requestType = com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto.class,
      responseType = com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByEmailMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getGetUserByEmailMethod;
    if ((getGetUserByEmailMethod = UserServiceGrpc.getGetUserByEmailMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByEmailMethod = UserServiceGrpc.getGetUserByEmailMethod) == null) {
          UserServiceGrpc.getGetUserByEmailMethod = getGetUserByEmailMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "UserService", "getUserByEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("getUserByEmail"))
                  .build();
          }
        }
     }
     return getGetUserByEmailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteUser",
      requestType = com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto.class,
      responseType = com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto,
      com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> getDeleteUserMethod;
    if ((getDeleteUserMethod = UserServiceGrpc.getDeleteUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getDeleteUserMethod = UserServiceGrpc.getDeleteUserMethod) == null) {
          UserServiceGrpc.getDeleteUserMethod = getDeleteUserMethod = 
              io.grpc.MethodDescriptor.<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto, com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "UserService", "deleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("deleteUser"))
                  .build();
          }
        }
     }
     return getDeleteUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    return new UserServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UserServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UserServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class UserServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUserById(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByIdMethod(), responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByEmailMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto,
                com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>(
                  this, METHODID_GET_USER_BY_ID)))
          .addMethod(
            getGetUserByEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto,
                com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>(
                  this, METHODID_GET_USER_BY_EMAIL)))
          .addMethod(
            getDeleteUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto,
                com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto>(
                  this, METHODID_DELETE_USER)))
          .build();
    }
  }

  /**
   */
  public static final class UserServiceStub extends io.grpc.stub.AbstractStub<UserServiceStub> {
    private UserServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     */
    public void getUserById(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto getUserById(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto getUserByEmail(com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByEmailMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto deleteUser(com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserServiceFutureStub extends io.grpc.stub.AbstractStub<UserServiceFutureStub> {
    private UserServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getUserById(
        com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto> getUserByEmail(
        com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto> deleteUser(
        com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_BY_ID = 0;
  private static final int METHODID_GET_USER_BY_EMAIL = 1;
  private static final int METHODID_DELETE_USER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_BY_ID:
          serviceImpl.getUserById((com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByIdRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>) responseObserver);
          break;
        case METHODID_GET_USER_BY_EMAIL:
          serviceImpl.getUserByEmail((com.example.authenticationclient.UserComponent.protoComponents.User.GetUserByEmailRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.UserResponseProto>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.authenticationclient.UserComponent.protoComponents.User.DeleteUserResponseProto>) responseObserver);
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

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.authenticationclient.UserComponent.protoComponents.User.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
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
