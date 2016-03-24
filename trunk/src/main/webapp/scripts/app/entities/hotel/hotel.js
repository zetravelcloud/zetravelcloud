'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('findHotel', {
	            parent: 'entity',
	            url: '/find-hotel',
	            data: {
	                authorities: ['ROLE_USER'],
	                pageTitle: 'zetravelcloudApp.hotel.home.title'
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/entities/hotel/find-hotel.html',
	                    controller: 'HotelController'
	                }
	            },
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                    $translatePartialLoader.addPart('hotel');
	                    $translatePartialLoader.addPart('global');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('hotel', {
                parent: 'entity',
                url: '/hotels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.hotel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotel/hotels.html',
                        controller: 'HotelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotel');
                        $translatePartialLoader.addPart('proposal');
                        $translatePartialLoader.addPart('proposedRoom');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hotel.detail', {
                parent: 'entity',
                url: '/hotel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.hotel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotel/hotel-detail.html',
                        controller: 'HotelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Hotel', function($stateParams, Hotel) {
                        return Hotel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hotel.new', {
                parent: 'hotel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-dialog.html',
                        controller: 'HotelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    address: null,
                                    stars: null,
                                    provider: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('hotel');
                    })
                }]
            })
            .state('hotel.edit', {
                parent: 'hotel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-dialog.html',
                        controller: 'HotelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Hotel', function(Hotel) {
                                return Hotel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hotel.delete', {
                parent: 'hotel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-delete-dialog.html',
                        controller: 'HotelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Hotel', function(Hotel) {
                                return Hotel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hotel.proposal', {
                parent: 'hotel',
                url: '/proposal',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal','ProposalService', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/proposal/proposal-dialog.html',
                        controller: 'ProposalDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function (ProposalService) {
                                return ProposalService.getProposal();
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('hotel');
                    })
                }]
            });
    });
