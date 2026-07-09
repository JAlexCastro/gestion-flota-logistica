import { useEffect, useState } from "react";
import { listarVehiculos } from "../../services/vehiculoService";
import "../Vehiculos/VehiculoForm.css";

function MantencionForm({ onSubmit, mantencion }) {

    const [vehiculos, setVehiculos] = useState([]);

    const initialForm = {
        vehiculoId: "",
        fecha: "",
        kilometraje: "",
        tipo: "",
        descripcion: "",
        taller: ""
    };

    const [form, setForm] = useState(initialForm);

    const [errores, setErrores] = useState({});

    useEffect(() => {
        cargarVehiculos();
    }, []);

    useEffect(() => {

        if (mantencion) {

            setForm({
                vehiculoId: mantencion.vehiculoId || "",
                fecha: mantencion.fecha || "",
                kilometraje: mantencion.kilometraje || "",
                tipo: mantencion.tipo || "",
                descripcion: mantencion.descripcion || "",
                taller: mantencion.taller || ""
            });

        } else {

            setForm(initialForm);

        }

        setErrores({});

    }, [mantencion]);

    const cargarVehiculos = async () => {

        try {

            const response = await listarVehiculos();

            setVehiculos(response.data);

        } catch (error) {

            console.error(error);

            setVehiculos([]);

        }

    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        setForm({
            ...form,
            [name]: value
        });

        setErrores({
            ...errores,
            [name]: ""
        });

    };

    const validar = () => {

        const nuevosErrores = {};

        if (!form.vehiculoId) {
            nuevosErrores.vehiculoId = "Seleccione un vehículo.";
        }

        if (!form.kilometraje) {
            nuevosErrores.kilometraje = "Ingrese el kilometraje.";
        } else if (Number(form.kilometraje) <= 0) {
            nuevosErrores.kilometraje = "Debe ser mayor a 0.";
        }

        if (!form.tipo) {
            nuevosErrores.tipo = "Seleccione el tipo de mantenimiento.";
        }

        if (!form.descripcion.trim()) {
            nuevosErrores.descripcion = "Ingrese una descripción.";
        }

        setErrores(nuevosErrores);

        return Object.keys(nuevosErrores).length === 0;

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        if (!validar()) return;

        onSubmit(form);

    };

    return (

        <form className="vehiculo-form" onSubmit={handleSubmit}>

            <div>

                <select
                    name="vehiculoId"
                    value={form.vehiculoId}
                    onChange={handleChange}
                >

                    <option value="">
                        Seleccione Vehículo
                    </option>

                    {vehiculos.map((v) => (

                        <option
                            key={v.id}
                            value={v.id}
                        >
                            {v.patente} - {v.marca}
                        </option>

                    ))}

                </select>

                {errores.vehiculoId &&
                    <span className="error">{errores.vehiculoId}</span>
                }

            </div>

            <div>

                <input
                    type="date"
                    name="fecha"
                    value={form.fecha}
                    onChange={handleChange}
                />

            </div>

            <div>

                <input
                    type="number"
                    min="1"
                    name="kilometraje"
                    placeholder="Kilometraje"
                    value={form.kilometraje}
                    onChange={handleChange}
                />

                {errores.kilometraje &&
                    <span className="error">{errores.kilometraje}</span>
                }

            </div>

            <div>

                <select
                    name="tipo"
                    value={form.tipo}
                    onChange={handleChange}
                >

                    <option value="">
                        Seleccione tipo de mantenimiento
                    </option>

                    <option value="PREVENTIVO">
                        Preventivo
                    </option>

                    <option value="CORRECTIVO">
                        Correctivo
                    </option>

                    <option value="PREDICTIVO">
                        Predictivo
                    </option>

                </select>

                {errores.tipo &&
                    <span className="error">{errores.tipo}</span>
                }

            </div>

            <div>

                <input
                    type="text"
                    name="taller"
                    placeholder="Taller (Opcional)"
                    value={form.taller}
                    onChange={handleChange}
                />

            </div>

            <div>

                <input
                    type="text"
                    name="descripcion"
                    maxLength={200}
                    placeholder="Descripción"
                    value={form.descripcion}
                    onChange={handleChange}
                />

                {errores.descripcion &&
                    <span className="error">{errores.descripcion}</span>
                }

            </div>

            <button type="submit">

                {mantencion ? "Actualizar" : "Guardar"}

            </button>

        </form>

    );

}

export default MantencionForm;