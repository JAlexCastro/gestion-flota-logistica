import { useEffect, useState } from "react";

import Modal from "../../components/Modal/Modal";
import VehiculoForm from "./VehiculoForm";
import VehiculoTable from "./VehiculoTable";
import AsignarConductorForm from "./AsignarConductorForm";

import {
    listarVehiculos,
    crearVehiculo,
    actualizarVehiculo,
    eliminarVehiculo,
    asignarConductor
} from "../../services/vehiculoService";

import "./VehiculosPage.css";

function VehiculosPage() {

    const [vehiculos, setVehiculos] = useState([]);

    // Modal Crear / Editar
    const [openModal, setOpenModal] = useState(false);
    const [vehiculoEdit, setVehiculoEdit] = useState(null);

    // Modal Asignar Conductor
    const [openAsignar, setOpenAsignar] = useState(false);
    const [vehiculoAsignar, setVehiculoAsignar] = useState(null);

    useEffect(() => {
        cargarVehiculos();
    }, []);

    const cargarVehiculos = async () => {
        const response = await listarVehiculos();
        setVehiculos(response.data);
    };

    // Abrir modal crear
    const handleOpenCreate = () => {
        setVehiculoEdit(null);
        setOpenModal(true);
    };

    // Abrir modal editar
    const handleEdit = (vehiculo) => {
        setVehiculoEdit(vehiculo);
        setOpenModal(true);
    };

    // Guardar (Crear / Editar)
    const handleSubmit = async (vehiculo) => {

        if (vehiculoEdit) {
            await actualizarVehiculo(vehiculoEdit.id, vehiculo);
        } else {
            await crearVehiculo(vehiculo);
        }

        setOpenModal(false);
        setVehiculoEdit(null);

        cargarVehiculos();
    };

    // Eliminar
    const handleDelete = async (id) => {

        await eliminarVehiculo(id);

        cargarVehiculos();

    };

    // Abrir modal asignar conductor
    const handleAsignar = (vehiculo) => {

        setVehiculoAsignar(vehiculo);

        setOpenAsignar(true);

    };

    // Guardar asignación
    const handleGuardarAsignacion = async (conductorId) => {

        await asignarConductor(
            vehiculoAsignar.id,
            conductorId
        );

        setOpenAsignar(false);
        setVehiculoAsignar(null);

        cargarVehiculos();

    };

    return (

        <div className="vehiculos-page">

            <div className="page-header">

                <h1>Vehículos</h1>

                <button onClick={handleOpenCreate}>
                    Nuevo Vehículo
                </button>

            </div>

            <VehiculoTable
                vehiculos={vehiculos}
                onEdit={handleEdit}
                onDelete={handleDelete}
                onAsignar={handleAsignar}
            />

            {/* Modal Crear / Editar */}

            <Modal
                isOpen={openModal}
                title={vehiculoEdit ? "Editar Vehículo" : "Nuevo Vehículo"}
                onClose={() => setOpenModal(false)}
            >

                <VehiculoForm
                    vehiculo={vehiculoEdit}
                    onSubmit={handleSubmit}
                />

            </Modal>

            {/* Modal Asignar Conductor */}

            <Modal
                isOpen={openAsignar}
                title="Asignar Conductor"
                onClose={() => setOpenAsignar(false)}
            >

                <AsignarConductorForm
                    vehiculo={vehiculoAsignar}
                    onSubmit={handleGuardarAsignacion}
                />

            </Modal>

        </div>

    );
}

export default VehiculosPage;