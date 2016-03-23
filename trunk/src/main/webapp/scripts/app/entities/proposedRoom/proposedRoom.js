'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('proposedRoom', {
                parent: 'entity',
                url: '/proposedRooms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.proposedRoom.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proposedRoom/proposedRooms.html',
                        controller: 'ProposedRoomController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proposedRoom');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('proposedRoom.detail', {
                parent: 'entity',
                url: '/proposedRoom/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.proposedRoom.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proposedRoom/proposedRoom-detail.html',
                        controller: 'ProposedRoomDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proposedRoom');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProposedRoom', function($stateParams, ProposedRoom) {
                        return ProposedRoom.get({id : $stateParams.id});
                    }]
                }
            })
            .state('proposedRoom.new', {
                parent: 'proposedRoom',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposedRoom/proposedRoom-dialog.html',
                        controller: 'ProposedRoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    hotelName: null,
                                    type: null,
                                    url: null,
                                    price: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('proposedRoom', null, { reload: true });
                    }, function() {
                        $state.go('proposedRoom');
                    })
                }]
            })
            .state('proposedRoom.edit', {
                parent: 'proposedRoom',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposedRoom/proposedRoom-dialog.html',
                        controller: 'ProposedRoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProposedRoom', function(ProposedRoom) {
                                return ProposedRoom.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proposedRoom', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('proposedRoom.delete', {
                parent: 'proposedRoom',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposedRoom/proposedRoom-delete-dialog.html',
                        controller: 'ProposedRoomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProposedRoom', function(ProposedRoom) {
                                return ProposedRoom.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proposedRoom', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
